import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAccounts } from '@/shared/model/CMSProBaseService/accounts.model';
import AccountsService from './accounts.service';

@Component
export default class AccountsDetails extends Vue {
  @Inject('accountsService') private accountsService: () => AccountsService;
  public accounts: IAccounts = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accountsId) {
        vm.retrieveAccounts(to.params.accountsId);
      }
    });
  }

  public retrieveAccounts(accountsId) {
    this.accountsService()
      .find(accountsId)
      .then(res => {
        this.accounts = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
