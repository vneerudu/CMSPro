import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUsers } from '@/shared/model/CMSProBaseService/users.model';
import UsersService from './users.service';

@Component
export default class UsersDetails extends Vue {
  @Inject('usersService') private usersService: () => UsersService;
  public users: IUsers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usersId) {
        vm.retrieveUsers(to.params.usersId);
      }
    });
  }

  public retrieveUsers(usersId) {
    this.usersService()
      .find(usersId)
      .then(res => {
        this.users = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
