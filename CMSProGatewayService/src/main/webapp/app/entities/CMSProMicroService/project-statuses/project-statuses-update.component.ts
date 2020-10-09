import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IProjectStatuses, ProjectStatuses } from '@/shared/model/CMSProMicroService/project-statuses.model';
import ProjectStatusesService from './project-statuses.service';

const validations: any = {
  projectStatuses: {
    status_id: {
      required,
      numeric,
    },
    status_code: {
      required,
    },
    description: {
      required,
    },
    is_active: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProjectStatusesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectStatusesService') private projectStatusesService: () => ProjectStatusesService;
  public projectStatuses: IProjectStatuses = new ProjectStatuses();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectStatusesId) {
        vm.retrieveProjectStatuses(to.params.projectStatusesId);
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
    if (this.projectStatuses.id) {
      this.projectStatusesService()
        .update(this.projectStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectStatusesService()
        .create(this.projectStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProjectStatuses(projectStatusesId): void {
    this.projectStatusesService()
      .find(projectStatusesId)
      .then(res => {
        this.projectStatuses = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
