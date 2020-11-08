/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RFIDetailComponent from '@/entities/CMSProBaseService/rfi/rfi-details.vue';
import RFIClass from '@/entities/CMSProBaseService/rfi/rfi-details.component';
import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RFI Management Detail Component', () => {
    let wrapper: Wrapper<RFIClass>;
    let comp: RFIClass;
    let rFIServiceStub: SinonStubbedInstance<RFIService>;

    beforeEach(() => {
      rFIServiceStub = sinon.createStubInstance<RFIService>(RFIService);

      wrapper = shallowMount<RFIClass>(RFIDetailComponent, { store, i18n, localVue, provide: { rFIService: () => rFIServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRFI = { id: '123' };
        rFIServiceStub.find.resolves(foundRFI);

        // WHEN
        comp.retrieveRFI('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rFI).toBe(foundRFI);
      });
    });
  });
});
