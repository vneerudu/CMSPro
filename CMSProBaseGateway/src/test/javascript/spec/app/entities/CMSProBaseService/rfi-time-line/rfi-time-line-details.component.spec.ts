/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RFITimeLineDetailComponent from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line-details.vue';
import RFITimeLineClass from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line-details.component';
import RFITimeLineService from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RFITimeLine Management Detail Component', () => {
    let wrapper: Wrapper<RFITimeLineClass>;
    let comp: RFITimeLineClass;
    let rFITimeLineServiceStub: SinonStubbedInstance<RFITimeLineService>;

    beforeEach(() => {
      rFITimeLineServiceStub = sinon.createStubInstance<RFITimeLineService>(RFITimeLineService);

      wrapper = shallowMount<RFITimeLineClass>(RFITimeLineDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rFITimeLineService: () => rFITimeLineServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRFITimeLine = { id: '123' };
        rFITimeLineServiceStub.find.resolves(foundRFITimeLine);

        // WHEN
        comp.retrieveRFITimeLine('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rFITimeLine).toBe(foundRFITimeLine);
      });
    });
  });
});
