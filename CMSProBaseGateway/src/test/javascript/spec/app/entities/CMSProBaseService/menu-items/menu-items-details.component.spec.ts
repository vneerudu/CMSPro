/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MenuItemsDetailComponent from '@/entities/CMSProBaseService/menu-items/menu-items-details.vue';
import MenuItemsClass from '@/entities/CMSProBaseService/menu-items/menu-items-details.component';
import MenuItemsService from '@/entities/CMSProBaseService/menu-items/menu-items.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MenuItems Management Detail Component', () => {
    let wrapper: Wrapper<MenuItemsClass>;
    let comp: MenuItemsClass;
    let menuItemsServiceStub: SinonStubbedInstance<MenuItemsService>;

    beforeEach(() => {
      menuItemsServiceStub = sinon.createStubInstance<MenuItemsService>(MenuItemsService);

      wrapper = shallowMount<MenuItemsClass>(MenuItemsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { menuItemsService: () => menuItemsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMenuItems = { id: '123' };
        menuItemsServiceStub.find.resolves(foundMenuItems);

        // WHEN
        comp.retrieveMenuItems('123');
        await comp.$nextTick();

        // THEN
        expect(comp.menuItems).toBe(foundMenuItems);
      });
    });
  });
});
