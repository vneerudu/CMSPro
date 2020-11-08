import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAccountStatuses, AccountStatuses } from '@/shared/model/CMSProBaseService/account-statuses.model';
import AccountStatusesService from './account-statuses.service';

const validations: any = {
  accountStatuses: {
    code: {},
    description: {},
    isActive: {},
  },
};

@Component({
  validations,
})
export default class AccountStatusesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accountStatusesService') private accountStatusesService: () => AccountStatusesService;
  public accountStatuses: IAccountStatuses = new AccountStatuses();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accountStatusesId) {
        vm.retrieveAccountStatuses(to.params.accountStatusesId);
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
    if (this.accountStatuses.id) {
      this.accountStatusesService()
        .update(this.accountStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.accountStatusesService()
        .create(this.accountStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAccountStatuses(accountStatusesId): void {
    this.accountStatusesService()
      .find(accountStatusesId)
      .then(res => {
        this.accountStatuses = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
