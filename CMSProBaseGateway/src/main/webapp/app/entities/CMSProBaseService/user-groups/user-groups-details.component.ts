import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';
import UserGroupsService from './user-groups.service';

@Component
export default class UserGroupsDetails extends Vue {
  @Inject('userGroupsService') private userGroupsService: () => UserGroupsService;
  public userGroups: IUserGroups = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupsId) {
        vm.retrieveUserGroups(to.params.userGroupsId);
      }
    });
  }

  public retrieveUserGroups(userGroupsId) {
    this.userGroupsService()
      .find(userGroupsId)
      .then(res => {
        this.userGroups = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
