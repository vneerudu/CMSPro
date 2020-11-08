import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';
import UserRolesService from './user-roles.service';

@Component
export default class UserRolesDetails extends Vue {
  @Inject('userRolesService') private userRolesService: () => UserRolesService;
  public userRoles: IUserRoles = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userRolesId) {
        vm.retrieveUserRoles(to.params.userRolesId);
      }
    });
  }

  public retrieveUserRoles(userRolesId) {
    this.userRolesService()
      .find(userRolesId)
      .then(res => {
        this.userRoles = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
