/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UsersUpdateComponent from '@/entities/CMSProBaseService/users/users-update.vue';
import UsersClass from '@/entities/CMSProBaseService/users/users-update.component';
import UsersService from '@/entities/CMSProBaseService/users/users.service';

import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';

import AddressesService from '@/entities/CMSProBaseService/addresses/addresses.service';

import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';

import ProjectTeamsService from '@/entities/CMSProBaseService/project-teams/project-teams.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Users Management Update Component', () => {
    let wrapper: Wrapper<UsersClass>;
    let comp: UsersClass;
    let usersServiceStub: SinonStubbedInstance<UsersService>;

    beforeEach(() => {
      usersServiceStub = sinon.createStubInstance<UsersService>(UsersService);

      wrapper = shallowMount<UsersClass>(UsersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          usersService: () => usersServiceStub,

          userGroupsService: () => new UserGroupsService(),

          addressesService: () => new AddressesService(),

          accountsService: () => new AccountsService(),

          projectTeamsService: () => new ProjectTeamsService(),

          tasksService: () => new TasksService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.users = entity;
        usersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.users = entity;
        usersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
