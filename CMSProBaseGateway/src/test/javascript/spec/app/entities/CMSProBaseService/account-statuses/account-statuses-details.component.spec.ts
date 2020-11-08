/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AccountStatusesDetailComponent from '@/entities/CMSProBaseService/account-statuses/account-statuses-details.vue';
import AccountStatusesClass from '@/entities/CMSProBaseService/account-statuses/account-statuses-details.component';
import AccountStatusesService from '@/entities/CMSProBaseService/account-statuses/account-statuses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AccountStatuses Management Detail Component', () => {
    let wrapper: Wrapper<AccountStatusesClass>;
    let comp: AccountStatusesClass;
    let accountStatusesServiceStub: SinonStubbedInstance<AccountStatusesService>;

    beforeEach(() => {
      accountStatusesServiceStub = sinon.createStubInstance<AccountStatusesService>(AccountStatusesService);

      wrapper = shallowMount<AccountStatusesClass>(AccountStatusesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { accountStatusesService: () => accountStatusesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAccountStatuses = { id: '123' };
        accountStatusesServiceStub.find.resolves(foundAccountStatuses);

        // WHEN
        comp.retrieveAccountStatuses('123');
        await comp.$nextTick();

        // THEN
        expect(comp.accountStatuses).toBe(foundAccountStatuses);
      });
    });
  });
});
