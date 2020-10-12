import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ProjectStatusService from '../project-status/project-status.service';
import { IProjectStatus } from '@/shared/model/CMSProMicroService/project-status.model';

import AlertService from '@/shared/alert/alert.service';
import { IProjects, Projects } from '@/shared/model/CMSProMicroService/projects.model';
import ProjectsService from './projects.service';

const validations: any = {
  projects: {
    projectID: {
      required,
      numeric,
    },
    name: {
      required,
    },
    department: {},
    organization: {},
    startDate: {
      required,
    },
    finishDate: {},
  },
};

@Component({
  validations,
})
export default class ProjectsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectsService') private projectsService: () => ProjectsService;
  public projects: IProjects = new Projects();

  @Inject('projectStatusService') private projectStatusService: () => ProjectStatusService;

  public projectStatuses: IProjectStatus[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectsId) {
        vm.retrieveProjects(to.params.projectsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.projects.id) {
      this.projectsService()
        .update(this.projects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectsService()
        .create(this.projects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProjects(projectsId): void {
    this.projectsService()
      .find(projectsId)
      .then(res => {
        this.projects = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.projectStatusService()
      .retrieve()
      .then(res => {
        this.projectStatuses = res.data;
      });
  }
}
