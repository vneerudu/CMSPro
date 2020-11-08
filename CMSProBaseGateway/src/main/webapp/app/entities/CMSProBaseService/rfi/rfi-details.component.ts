import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';
import RFIService from './rfi.service';

@Component
export default class RFIDetails extends Vue {
  @Inject('rFIService') private rFIService: () => RFIService;
  public rFI: IRFI = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFIId) {
        vm.retrieveRFI(to.params.rFIId);
      }
    });
  }

  public retrieveRFI(rFIId) {
    this.rFIService()
      .find(rFIId)
      .then(res => {
        this.rFI = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
