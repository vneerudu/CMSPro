import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMenuItems } from '@/shared/model/CMSProBaseService/menu-items.model';
import MenuItemsService from './menu-items.service';

@Component
export default class MenuItemsDetails extends Vue {
  @Inject('menuItemsService') private menuItemsService: () => MenuItemsService;
  public menuItems: IMenuItems = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menuItemsId) {
        vm.retrieveMenuItems(to.params.menuItemsId);
      }
    });
  }

  public retrieveMenuItems(menuItemsId) {
    this.menuItemsService()
      .find(menuItemsId)
      .then(res => {
        this.menuItems = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
