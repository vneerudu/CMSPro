/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MenuItemsUpdateComponent from '@/entities/CMSProBaseService/menu-items/menu-items-update.vue';
import MenuItemsClass from '@/entities/CMSProBaseService/menu-items/menu-items-update.component';
import MenuItemsService from '@/entities/CMSProBaseService/menu-items/menu-items.service';

import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MenuItems Management Update Component', () => {
    let wrapper: Wrapper<MenuItemsClass>;
    let comp: MenuItemsClass;
    let menuItemsServiceStub: SinonStubbedInstance<MenuItemsService>;

    beforeEach(() => {
      menuItemsServiceStub = sinon.createStubInstance<MenuItemsService>(MenuItemsService);

      wrapper = shallowMount<MenuItemsClass>(MenuItemsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          menuItemsService: () => menuItemsServiceStub,

          userRolesService: () => new UserRolesService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.menuItems = entity;
        menuItemsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menuItemsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.menuItems = entity;
        menuItemsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menuItemsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
