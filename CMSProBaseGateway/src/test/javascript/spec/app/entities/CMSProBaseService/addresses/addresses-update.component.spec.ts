/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AddressesUpdateComponent from '@/entities/CMSProBaseService/addresses/addresses-update.vue';
import AddressesClass from '@/entities/CMSProBaseService/addresses/addresses-update.component';
import AddressesService from '@/entities/CMSProBaseService/addresses/addresses.service';

import AddressTypesService from '@/entities/CMSProBaseService/address-types/address-types.service';

import StatesService from '@/entities/CMSProBaseService/states/states.service';

import CountryService from '@/entities/CMSProBaseService/country/country.service';

import UsersService from '@/entities/CMSProBaseService/users/users.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Addresses Management Update Component', () => {
    let wrapper: Wrapper<AddressesClass>;
    let comp: AddressesClass;
    let addressesServiceStub: SinonStubbedInstance<AddressesService>;

    beforeEach(() => {
      addressesServiceStub = sinon.createStubInstance<AddressesService>(AddressesService);

      wrapper = shallowMount<AddressesClass>(AddressesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          addressesService: () => addressesServiceStub,

          addressTypesService: () => new AddressTypesService(),

          statesService: () => new StatesService(),

          countryService: () => new CountryService(),

          usersService: () => new UsersService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.addresses = entity;
        addressesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.addresses = entity;
        addressesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
