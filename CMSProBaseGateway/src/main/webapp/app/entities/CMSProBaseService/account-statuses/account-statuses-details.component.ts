import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAccountStatuses } from '@/shared/model/CMSProBaseService/account-statuses.model';
import AccountStatusesService from './account-statuses.service';

@Component
export default class AccountStatusesDetails extends Vue {
  @Inject('accountStatusesService') private accountStatusesService: () => AccountStatusesService;
  public accountStatuses: IAccountStatuses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accountStatusesId) {
        vm.retrieveAccountStatuses(to.params.accountStatusesId);
      }
    });
  }

  public retrieveAccountStatuses(accountStatusesId) {
    this.accountStatusesService()
      .find(accountStatusesId)
      .then(res => {
        this.accountStatuses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
