/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AddressesDetailComponent from '@/entities/CMSProBaseService/addresses/addresses-details.vue';
import AddressesClass from '@/entities/CMSProBaseService/addresses/addresses-details.component';
import AddressesService from '@/entities/CMSProBaseService/addresses/addresses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Addresses Management Detail Component', () => {
    let wrapper: Wrapper<AddressesClass>;
    let comp: AddressesClass;
    let addressesServiceStub: SinonStubbedInstance<AddressesService>;

    beforeEach(() => {
      addressesServiceStub = sinon.createStubInstance<AddressesService>(AddressesService);

      wrapper = shallowMount<AddressesClass>(AddressesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { addressesService: () => addressesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAddresses = { id: '123' };
        addressesServiceStub.find.resolves(foundAddresses);

        // WHEN
        comp.retrieveAddresses('123');
        await comp.$nextTick();

        // THEN
        expect(comp.addresses).toBe(foundAddresses);
      });
    });
  });
});
