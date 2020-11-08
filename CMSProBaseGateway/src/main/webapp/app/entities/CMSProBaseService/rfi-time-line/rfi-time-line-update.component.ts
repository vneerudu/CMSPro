import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { IRFITimeLine, RFITimeLine } from '@/shared/model/CMSProBaseService/rfi-time-line.model';
import RFITimeLineService from './rfi-time-line.service';

const validations: any = {
  rFITimeLine: {
    createdBy: {
      required,
    },
    message: {
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
export default class RFITimeLineUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rFITimeLineService') private rFITimeLineService: () => RFITimeLineService;
  public rFITimeLine: IRFITimeLine = new RFITimeLine();

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFITimeLineId) {
        vm.retrieveRFITimeLine(to.params.rFITimeLineId);
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
    if (this.rFITimeLine.id) {
      this.rFITimeLineService()
        .update(this.rFITimeLine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rFITimeLineService()
        .create(this.rFITimeLine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRFITimeLine(rFITimeLineId): void {
    this.rFITimeLineService()
      .find(rFITimeLineId)
      .then(res => {
        this.rFITimeLine = res;
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
