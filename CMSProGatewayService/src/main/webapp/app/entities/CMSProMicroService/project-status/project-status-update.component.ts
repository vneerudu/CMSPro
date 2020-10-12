import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IProjectStatus, ProjectStatus } from '@/shared/model/CMSProMicroService/project-status.model';
import ProjectStatusService from './project-status.service';

const validations: any = {
  projectStatus: {
    code: {
      required,
    },
    description: {
      required,
    },
    isActive: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectStatusUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectStatusService') private projectStatusService: () => ProjectStatusService;
  public projectStatus: IProjectStatus = new ProjectStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectStatusId) {
        vm.retrieveProjectStatus(to.params.projectStatusId);
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
    if (this.projectStatus.id) {
      this.projectStatusService()
        .update(this.projectStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectStatusService()
        .create(this.projectStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProjectStatus(projectStatusId): void {
    this.projectStatusService()
      .find(projectStatusId)
      .then(res => {
        this.projectStatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
