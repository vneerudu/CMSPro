import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRootCauseGroups } from '@/shared/model/CMSProBaseService/root-cause-groups.model';
import RootCauseGroupsService from './root-cause-groups.service';

@Component
export default class RootCauseGroupsDetails extends Vue {
  @Inject('rootCauseGroupsService') private rootCauseGroupsService: () => RootCauseGroupsService;
  public rootCauseGroups: IRootCauseGroups = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rootCauseGroupsId) {
        vm.retrieveRootCauseGroups(to.params.rootCauseGroupsId);
      }
    });
  }

  public retrieveRootCauseGroups(rootCauseGroupsId) {
    this.rootCauseGroupsService()
      .find(rootCauseGroupsId)
      .then(res => {
        this.rootCauseGroups = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
