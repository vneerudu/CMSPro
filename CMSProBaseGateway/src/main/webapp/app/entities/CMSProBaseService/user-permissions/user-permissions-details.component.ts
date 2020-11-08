import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserPermissions } from '@/shared/model/CMSProBaseService/user-permissions.model';
import UserPermissionsService from './user-permissions.service';

@Component
export default class UserPermissionsDetails extends Vue {
  @Inject('userPermissionsService') private userPermissionsService: () => UserPermissionsService;
  public userPermissions: IUserPermissions = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userPermissionsId) {
        vm.retrieveUserPermissions(to.params.userPermissionsId);
      }
    });
  }

  public retrieveUserPermissions(userPermissionsId) {
    this.userPermissionsService()
      .find(userPermissionsId)
      .then(res => {
        this.userPermissions = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
