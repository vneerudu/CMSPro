/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AccountsDetailComponent from '@/entities/CMSProBaseService/accounts/accounts-details.vue';
import AccountsClass from '@/entities/CMSProBaseService/accounts/accounts-details.component';
import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Accounts Management Detail Component', () => {
    let wrapper: Wrapper<AccountsClass>;
    let comp: AccountsClass;
    let accountsServiceStub: SinonStubbedInstance<AccountsService>;

    beforeEach(() => {
      accountsServiceStub = sinon.createStubInstance<AccountsService>(AccountsService);

      wrapper = shallowMount<AccountsClass>(AccountsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { accountsService: () => accountsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAccounts = { id: '123' };
        accountsServiceStub.find.resolves(foundAccounts);

        // WHEN
        comp.retrieveAccounts('123');
        await comp.$nextTick();

        // THEN
        expect(comp.accounts).toBe(foundAccounts);
      });
    });
  });
});
