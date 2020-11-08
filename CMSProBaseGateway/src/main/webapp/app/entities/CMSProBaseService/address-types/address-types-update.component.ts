import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAddressTypes, AddressTypes } from '@/shared/model/CMSProBaseService/address-types.model';
import AddressTypesService from './address-types.service';

const validations: any = {
  addressTypes: {
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
export default class AddressTypesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('addressTypesService') private addressTypesService: () => AddressTypesService;
  public addressTypes: IAddressTypes = new AddressTypes();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressTypesId) {
        vm.retrieveAddressTypes(to.params.addressTypesId);
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
    if (this.addressTypes.id) {
      this.addressTypesService()
        .update(this.addressTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAddressTypes.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.addressTypesService()
        .create(this.addressTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAddressTypes.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAddressTypes(addressTypesId): void {
    this.addressTypesService()
      .find(addressTypesId)
      .then(res => {
        this.addressTypes = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
