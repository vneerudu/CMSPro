/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AddressTypesUpdateComponent from '@/entities/CMSProBaseService/address-types/address-types-update.vue';
import AddressTypesClass from '@/entities/CMSProBaseService/address-types/address-types-update.component';
import AddressTypesService from '@/entities/CMSProBaseService/address-types/address-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AddressTypes Management Update Component', () => {
    let wrapper: Wrapper<AddressTypesClass>;
    let comp: AddressTypesClass;
    let addressTypesServiceStub: SinonStubbedInstance<AddressTypesService>;

    beforeEach(() => {
      addressTypesServiceStub = sinon.createStubInstance<AddressTypesService>(AddressTypesService);

      wrapper = shallowMount<AddressTypesClass>(AddressTypesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          addressTypesService: () => addressTypesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.addressTypes = entity;
        addressTypesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressTypesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.addressTypes = entity;
        addressTypesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressTypesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
