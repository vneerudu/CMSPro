import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UserRolesService from '../user-roles/user-roles.service';
import { IUserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';

import AccountsService from '../accounts/accounts.service';
import { IAccounts } from '@/shared/model/CMSProBaseService/accounts.model';

import UsersService from '../users/users.service';
import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

import AlertService from '@/shared/alert/alert.service';
import { IUserGroups, UserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';
import UserGroupsService from './user-groups.service';

const validations: any = {
  userGroups: {
    code: {
      required,
    },
    description: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class UserGroupsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userGroupsService') private userGroupsService: () => UserGroupsService;
  public userGroups: IUserGroups = new UserGroups();

  @Inject('userRolesService') private userRolesService: () => UserRolesService;

  public userRoles: IUserRoles[] = [];

  @Inject('accountsService') private accountsService: () => AccountsService;

  public accounts: IAccounts[] = [];

  @Inject('usersService') private usersService: () => UsersService;

  public users: IUsers[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userGroupsId) {
        vm.retrieveUserGroups(to.params.userGroupsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.userGroups.id) {
      this.userGroupsService()
        .update(this.userGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userGroupsService()
        .create(this.userGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUserGroups(userGroupsId): void {
    this.userGroupsService()
      .find(userGroupsId)
      .then(res => {
        this.userGroups = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userRolesService()
      .retrieve()
      .then(res => {
        this.userRoles = res.data;
      });
    this.accountsService()
      .retrieve()
      .then(res => {
        this.accounts = res.data;
      });
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
