import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRFITimeLine } from '@/shared/model/CMSProBaseService/rfi-time-line.model';
import RFITimeLineService from './rfi-time-line.service';

@Component
export default class RFITimeLineDetails extends Vue {
  @Inject('rFITimeLineService') private rFITimeLineService: () => RFITimeLineService;
  public rFITimeLine: IRFITimeLine = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFITimeLineId) {
        vm.retrieveRFITimeLine(to.params.rFITimeLineId);
      }
    });
  }

  public retrieveRFITimeLine(rFITimeLineId) {
    this.rFITimeLineService()
      .find(rFITimeLineId)
      .then(res => {
        this.rFITimeLine = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
