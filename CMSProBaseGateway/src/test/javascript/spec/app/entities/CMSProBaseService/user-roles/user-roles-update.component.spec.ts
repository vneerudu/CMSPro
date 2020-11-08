/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserRolesUpdateComponent from '@/entities/CMSProBaseService/user-roles/user-roles-update.vue';
import UserRolesClass from '@/entities/CMSProBaseService/user-roles/user-roles-update.component';
import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';

import UserPermissionsService from '@/entities/CMSProBaseService/user-permissions/user-permissions.service';

import MenuItemsService from '@/entities/CMSProBaseService/menu-items/menu-items.service';

import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('UserRoles Management Update Component', () => {
    let wrapper: Wrapper<UserRolesClass>;
    let comp: UserRolesClass;
    let userRolesServiceStub: SinonStubbedInstance<UserRolesService>;

    beforeEach(() => {
      userRolesServiceStub = sinon.createStubInstance<UserRolesService>(UserRolesService);

      wrapper = shallowMount<UserRolesClass>(UserRolesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          userRolesService: () => userRolesServiceStub,

          userPermissionsService: () => new UserPermissionsService(),

          menuItemsService: () => new MenuItemsService(),

          userGroupsService: () => new UserGroupsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.userRoles = entity;
        userRolesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userRolesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userRoles = entity;
        userRolesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userRolesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
