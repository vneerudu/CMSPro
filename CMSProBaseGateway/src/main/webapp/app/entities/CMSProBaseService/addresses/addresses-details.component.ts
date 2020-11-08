import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAddresses } from '@/shared/model/CMSProBaseService/addresses.model';
import AddressesService from './addresses.service';

@Component
export default class AddressesDetails extends Vue {
  @Inject('addressesService') private addressesService: () => AddressesService;
  public addresses: IAddresses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressesId) {
        vm.retrieveAddresses(to.params.addressesId);
      }
    });
  }

  public retrieveAddresses(addressesId) {
    this.addressesService()
      .find(addressesId)
      .then(res => {
        this.addresses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
