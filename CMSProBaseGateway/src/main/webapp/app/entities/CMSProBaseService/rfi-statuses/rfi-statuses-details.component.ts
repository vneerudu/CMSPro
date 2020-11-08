import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRFIStatuses } from '@/shared/model/CMSProBaseService/rfi-statuses.model';
import RFIStatusesService from './rfi-statuses.service';

@Component
export default class RFIStatusesDetails extends Vue {
  @Inject('rFIStatusesService') private rFIStatusesService: () => RFIStatusesService;
  public rFIStatuses: IRFIStatuses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFIStatusesId) {
        vm.retrieveRFIStatuses(to.params.rFIStatusesId);
      }
    });
  }

  public retrieveRFIStatuses(rFIStatusesId) {
    this.rFIStatusesService()
      .find(rFIStatusesId)
      .then(res => {
        this.rFIStatuses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
