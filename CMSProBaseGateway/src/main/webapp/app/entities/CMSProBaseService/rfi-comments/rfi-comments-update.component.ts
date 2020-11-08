import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { IRFIComments, RFIComments } from '@/shared/model/CMSProBaseService/rfi-comments.model';
import RFICommentsService from './rfi-comments.service';

const validations: any = {
  rFIComments: {
    createdBy: {
      required,
    },
    comment: {
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
export default class RFICommentsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rFICommentsService') private rFICommentsService: () => RFICommentsService;
  public rFIComments: IRFIComments = new RFIComments();

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFICommentsId) {
        vm.retrieveRFIComments(to.params.rFICommentsId);
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
    if (this.rFIComments.id) {
      this.rFICommentsService()
        .update(this.rFIComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rFICommentsService()
        .create(this.rFIComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRFIComments(rFICommentsId): void {
    this.rFICommentsService()
      .find(rFICommentsId)
      .then(res => {
        this.rFIComments = res;
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
