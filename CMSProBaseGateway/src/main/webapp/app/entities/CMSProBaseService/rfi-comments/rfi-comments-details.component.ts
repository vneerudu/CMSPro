import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRFIComments } from '@/shared/model/CMSProBaseService/rfi-comments.model';
import RFICommentsService from './rfi-comments.service';

@Component
export default class RFICommentsDetails extends Vue {
  @Inject('rFICommentsService') private rFICommentsService: () => RFICommentsService;
  public rFIComments: IRFIComments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFICommentsId) {
        vm.retrieveRFIComments(to.params.rFICommentsId);
      }
    });
  }

  public retrieveRFIComments(rFICommentsId) {
    this.rFICommentsService()
      .find(rFICommentsId)
      .then(res => {
        this.rFIComments = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
