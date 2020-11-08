/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AddressTypesDetailComponent from '@/entities/CMSProBaseService/address-types/address-types-details.vue';
import AddressTypesClass from '@/entities/CMSProBaseService/address-types/address-types-details.component';
import AddressTypesService from '@/entities/CMSProBaseService/address-types/address-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AddressTypes Management Detail Component', () => {
    let wrapper: Wrapper<AddressTypesClass>;
    let comp: AddressTypesClass;
    let addressTypesServiceStub: SinonStubbedInstance<AddressTypesService>;

    beforeEach(() => {
      addressTypesServiceStub = sinon.createStubInstance<AddressTypesService>(AddressTypesService);

      wrapper = shallowMount<AddressTypesClass>(AddressTypesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { addressTypesService: () => addressTypesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAddressTypes = { id: '123' };
        addressTypesServiceStub.find.resolves(foundAddressTypes);

        // WHEN
        comp.retrieveAddressTypes('123');
        await comp.$nextTick();

        // THEN
        expect(comp.addressTypes).toBe(foundAddressTypes);
      });
    });
  });
});
