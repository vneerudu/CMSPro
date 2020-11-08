import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UserRolesService from '../user-roles/user-roles.service';
import { IUserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';

import AlertService from '@/shared/alert/alert.service';
import { IUserPermissions, UserPermissions } from '@/shared/model/CMSProBaseService/user-permissions.model';
import UserPermissionsService from './user-permissions.service';

const validations: any = {
  userPermissions: {
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
export default class UserPermissionsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userPermissionsService') private userPermissionsService: () => UserPermissionsService;
  public userPermissions: IUserPermissions = new UserPermissions();

  @Inject('userRolesService') private userRolesService: () => UserRolesService;

  public userRoles: IUserRoles[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userPermissionsId) {
        vm.retrieveUserPermissions(to.params.userPermissionsId);
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
    if (this.userPermissions.id) {
      this.userPermissionsService()
        .update(this.userPermissions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userPermissionsService()
        .create(this.userPermissions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUserPermissions(userPermissionsId): void {
    this.userPermissionsService()
      .find(userPermissionsId)
      .then(res => {
        this.userPermissions = res;
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
  }
}
