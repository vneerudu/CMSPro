import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UsersService from '../users/users.service';
import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { IProjectTeams, ProjectTeams } from '@/shared/model/CMSProBaseService/project-teams.model';
import ProjectTeamsService from './project-teams.service';

const validations: any = {
  projectTeams: {
    name: {
      required,
    },
    createdBy: {
      required,
    },
    creationDate: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectTeamsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectTeamsService') private projectTeamsService: () => ProjectTeamsService;
  public projectTeams: IProjectTeams = new ProjectTeams();

  @Inject('usersService') private usersService: () => UsersService;

  public users: IUsers[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectTeamsId) {
        vm.retrieveProjectTeams(to.params.projectTeamsId);
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
    if (this.projectTeams.id) {
      this.projectTeamsService()
        .update(this.projectTeams)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectTeamsService()
        .create(this.projectTeams)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProjectTeams(projectTeamsId): void {
    this.projectTeamsService()
      .find(projectTeamsId)
      .then(res => {
        this.projectTeams = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
