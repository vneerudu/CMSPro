import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAddressTypes } from '@/shared/model/CMSProBaseService/address-types.model';
import AddressTypesService from './address-types.service';

@Component
export default class AddressTypesDetails extends Vue {
  @Inject('addressTypesService') private addressTypesService: () => AddressTypesService;
  public addressTypes: IAddressTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressTypesId) {
        vm.retrieveAddressTypes(to.params.addressTypesId);
      }
    });
  }

  public retrieveAddressTypes(addressTypesId) {
    this.addressTypesService()
      .find(addressTypesId)
      .then(res => {
        this.addressTypes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
