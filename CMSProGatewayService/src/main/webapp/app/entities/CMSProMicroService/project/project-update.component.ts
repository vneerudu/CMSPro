import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IProject, Project } from '@/shared/model/CMSProMicroService/project.model';
import ProjectService from './project.service';

const validations: any = {
  project: {
    name: {
      required,
    },
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
export default class ProjectUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectService') private projectService: () => ProjectService;
  public project: IProject = new Project();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectId) {
        vm.retrieveProject(to.params.projectId);
      }
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
    if (this.project.id) {
      this.projectService()
        .update(this.project)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectService()
        .create(this.project)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProject(projectId): void {
    this.projectService()
      .find(projectId)
      .then(res => {
        this.project = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
