/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StampsDetailComponent from '@/entities/CMSProBaseService/stamps/stamps-details.vue';
import StampsClass from '@/entities/CMSProBaseService/stamps/stamps-details.component';
import StampsService from '@/entities/CMSProBaseService/stamps/stamps.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Stamps Management Detail Component', () => {
    let wrapper: Wrapper<StampsClass>;
    let comp: StampsClass;
    let stampsServiceStub: SinonStubbedInstance<StampsService>;

    beforeEach(() => {
      stampsServiceStub = sinon.createStubInstance<StampsService>(StampsService);

      wrapper = shallowMount<StampsClass>(StampsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { stampsService: () => stampsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStamps = { id: '123' };
        stampsServiceStub.find.resolves(foundStamps);

        // WHEN
        comp.retrieveStamps('123');
        await comp.$nextTick();

        // THEN
        expect(comp.stamps).toBe(foundStamps);
      });
    });
  });
});
