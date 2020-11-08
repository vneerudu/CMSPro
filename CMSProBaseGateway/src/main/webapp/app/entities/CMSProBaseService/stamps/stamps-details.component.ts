import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStamps } from '@/shared/model/CMSProBaseService/stamps.model';
import StampsService from './stamps.service';

@Component
export default class StampsDetails extends Vue {
  @Inject('stampsService') private stampsService: () => StampsService;
  public stamps: IStamps = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stampsId) {
        vm.retrieveStamps(to.params.stampsId);
      }
    });
  }

  public retrieveStamps(stampsId) {
    this.stampsService()
      .find(stampsId)
      .then(res => {
        this.stamps = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
