/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AccountsUpdateComponent from '@/entities/CMSProBaseService/accounts/accounts-update.vue';
import AccountsClass from '@/entities/CMSProBaseService/accounts/accounts-update.component';
import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';

import LanguagesService from '@/entities/CMSProBaseService/languages/languages.service';

import LogosService from '@/entities/CMSProBaseService/logos/logos.service';

import AccountStatusesService from '@/entities/CMSProBaseService/account-statuses/account-statuses.service';

import UsersService from '@/entities/CMSProBaseService/users/users.service';

import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Accounts Management Update Component', () => {
    let wrapper: Wrapper<AccountsClass>;
    let comp: AccountsClass;
    let accountsServiceStub: SinonStubbedInstance<AccountsService>;

    beforeEach(() => {
      accountsServiceStub = sinon.createStubInstance<AccountsService>(AccountsService);

      wrapper = shallowMount<AccountsClass>(AccountsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          accountsService: () => accountsServiceStub,

          languagesService: () => new LanguagesService(),

          logosService: () => new LogosService(),

          accountStatusesService: () => new AccountStatusesService(),

          usersService: () => new UsersService(),

          userGroupsService: () => new UserGroupsService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.accounts = entity;
        accountsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accountsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.accounts = entity;
        accountsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accountsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
