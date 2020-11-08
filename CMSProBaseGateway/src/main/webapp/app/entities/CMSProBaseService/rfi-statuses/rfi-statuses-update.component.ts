import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { IRFIStatuses, RFIStatuses } from '@/shared/model/CMSProBaseService/rfi-statuses.model';
import RFIStatusesService from './rfi-statuses.service';

const validations: any = {
  rFIStatuses: {
    code: {
      required,
    },
    description: {
      required,
    },
    isActive: {},
  },
};

@Component({
  validations,
})
export default class RFIStatusesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rFIStatusesService') private rFIStatusesService: () => RFIStatusesService;
  public rFIStatuses: IRFIStatuses = new RFIStatuses();

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFIStatusesId) {
        vm.retrieveRFIStatuses(to.params.rFIStatusesId);
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
    if (this.rFIStatuses.id) {
      this.rFIStatusesService()
        .update(this.rFIStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rFIStatusesService()
        .create(this.rFIStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRFIStatuses(rFIStatusesId): void {
    this.rFIStatusesService()
      .find(rFIStatusesId)
      .then(res => {
        this.rFIStatuses = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.rFIService()
      .retrieve()
      .then(res => {
        this.rFIS = res.data;
      });
  }
}
