import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IStates, States } from '@/shared/model/CMSProBaseService/states.model';
import StatesService from './states.service';

const validations: any = {
  states: {
    code: {
      required,
    },
    description: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class StatesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('statesService') private statesService: () => StatesService;
  public states: IStates = new States();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.statesId) {
        vm.retrieveStates(to.params.statesId);
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
    if (this.states.id) {
      this.statesService()
        .update(this.states)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceStates.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.statesService()
        .create(this.states)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceStates.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStates(statesId): void {
    this.statesService()
      .find(statesId)
      .then(res => {
        this.states = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
