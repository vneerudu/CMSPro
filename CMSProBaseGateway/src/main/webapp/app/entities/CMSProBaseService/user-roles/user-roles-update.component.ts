import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UserPermissionsService from '../user-permissions/user-permissions.service';
import { IUserPermissions } from '@/shared/model/CMSProBaseService/user-permissions.model';

import MenuItemsService from '../menu-items/menu-items.service';
import { IMenuItems } from '@/shared/model/CMSProBaseService/menu-items.model';

import UserGroupsService from '../user-groups/user-groups.service';
import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';

import AlertService from '@/shared/alert/alert.service';
import { IUserRoles, UserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';
import UserRolesService from './user-roles.service';

const validations: any = {
  userRoles: {
    code: {
      required,
    },
    description: {
      required,
    },
    isActive: {},
  },
};

@Component({
  validations,
})
export default class UserRolesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userRolesService') private userRolesService: () => UserRolesService;
  public userRoles: IUserRoles = new UserRoles();

  @Inject('userPermissionsService') private userPermissionsService: () => UserPermissionsService;

  public userPermissions: IUserPermissions[] = [];

  @Inject('menuItemsService') private menuItemsService: () => MenuItemsService;

  public menuItems: IMenuItems[] = [];

  @Inject('userGroupsService') private userGroupsService: () => UserGroupsService;

  public userGroups: IUserGroups[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userRolesId) {
        vm.retrieveUserRoles(to.params.userRolesId);
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
    if (this.userRoles.id) {
      this.userRolesService()
        .update(this.userRoles)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userRolesService()
        .create(this.userRoles)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUserRoles(userRolesId): void {
    this.userRolesService()
      .find(userRolesId)
      .then(res => {
        this.userRoles = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userPermissionsService()
      .retrieve()
      .then(res => {
        this.userPermissions = res.data;
      });
    this.menuItemsService()
      .retrieve()
      .then(res => {
        this.menuItems = res.data;
      });
    this.userGroupsService()
      .retrieve()
      .then(res => {
        this.userGroups = res.data;
      });
  }
}
