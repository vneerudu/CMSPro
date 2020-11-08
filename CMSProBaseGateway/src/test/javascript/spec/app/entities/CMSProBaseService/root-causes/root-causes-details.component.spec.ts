/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RootCausesDetailComponent from '@/entities/CMSProBaseService/root-causes/root-causes-details.vue';
import RootCausesClass from '@/entities/CMSProBaseService/root-causes/root-causes-details.component';
import RootCausesService from '@/entities/CMSProBaseService/root-causes/root-causes.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RootCauses Management Detail Component', () => {
    let wrapper: Wrapper<RootCausesClass>;
    let comp: RootCausesClass;
    let rootCausesServiceStub: SinonStubbedInstance<RootCausesService>;

    beforeEach(() => {
      rootCausesServiceStub = sinon.createStubInstance<RootCausesService>(RootCausesService);

      wrapper = shallowMount<RootCausesClass>(RootCausesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rootCausesService: () => rootCausesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRootCauses = { id: '123' };
        rootCausesServiceStub.find.resolves(foundRootCauses);

        // WHEN
        comp.retrieveRootCauses('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rootCauses).toBe(foundRootCauses);
      });
    });
  });
});
