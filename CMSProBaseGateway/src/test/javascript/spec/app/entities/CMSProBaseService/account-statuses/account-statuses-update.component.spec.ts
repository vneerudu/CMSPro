/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AccountStatusesUpdateComponent from '@/entities/CMSProBaseService/account-statuses/account-statuses-update.vue';
import AccountStatusesClass from '@/entities/CMSProBaseService/account-statuses/account-statuses-update.component';
import AccountStatusesService from '@/entities/CMSProBaseService/account-statuses/account-statuses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AccountStatuses Management Update Component', () => {
    let wrapper: Wrapper<AccountStatusesClass>;
    let comp: AccountStatusesClass;
    let accountStatusesServiceStub: SinonStubbedInstance<AccountStatusesService>;

    beforeEach(() => {
      accountStatusesServiceStub = sinon.createStubInstance<AccountStatusesService>(AccountStatusesService);

      wrapper = shallowMount<AccountStatusesClass>(AccountStatusesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          accountStatusesService: () => accountStatusesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.accountStatuses = entity;
        accountStatusesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accountStatusesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.accountStatuses = entity;
        accountStatusesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(accountStatusesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
