import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UserRolesService from '../user-roles/user-roles.service';
import { IUserRoles } from '@/shared/model/CMSProBaseService/user-roles.model';

import AlertService from '@/shared/alert/alert.service';
import { IMenuItems, MenuItems } from '@/shared/model/CMSProBaseService/menu-items.model';
import MenuItemsService from './menu-items.service';

const validations: any = {
  menuItems: {
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
export default class MenuItemsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('menuItemsService') private menuItemsService: () => MenuItemsService;
  public menuItems: IMenuItems = new MenuItems();

  @Inject('userRolesService') private userRolesService: () => UserRolesService;

  public userRoles: IUserRoles[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menuItemsId) {
        vm.retrieveMenuItems(to.params.menuItemsId);
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
    if (this.menuItems.id) {
      this.menuItemsService()
        .update(this.menuItems)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.menuItemsService()
        .create(this.menuItems)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMenuItems(menuItemsId): void {
    this.menuItemsService()
      .find(menuItemsId)
      .then(res => {
        this.menuItems = res;
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
