/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserGroupsUpdateComponent from '@/entities/CMSProBaseService/user-groups/user-groups-update.vue';
import UserGroupsClass from '@/entities/CMSProBaseService/user-groups/user-groups-update.component';
import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';

import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';

import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';

import UsersService from '@/entities/CMSProBaseService/users/users.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('UserGroups Management Update Component', () => {
    let wrapper: Wrapper<UserGroupsClass>;
    let comp: UserGroupsClass;
    let userGroupsServiceStub: SinonStubbedInstance<UserGroupsService>;

    beforeEach(() => {
      userGroupsServiceStub = sinon.createStubInstance<UserGroupsService>(UserGroupsService);

      wrapper = shallowMount<UserGroupsClass>(UserGroupsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          userGroupsService: () => userGroupsServiceStub,

          userRolesService: () => new UserRolesService(),

          accountsService: () => new AccountsService(),

          usersService: () => new UsersService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.userGroups = entity;
        userGroupsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userGroups = entity;
        userGroupsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userGroupsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
