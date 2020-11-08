/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RFIStatusesDetailComponent from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses-details.vue';
import RFIStatusesClass from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses-details.component';
import RFIStatusesService from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RFIStatuses Management Detail Component', () => {
    let wrapper: Wrapper<RFIStatusesClass>;
    let comp: RFIStatusesClass;
    let rFIStatusesServiceStub: SinonStubbedInstance<RFIStatusesService>;

    beforeEach(() => {
      rFIStatusesServiceStub = sinon.createStubInstance<RFIStatusesService>(RFIStatusesService);

      wrapper = shallowMount<RFIStatusesClass>(RFIStatusesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rFIStatusesService: () => rFIStatusesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRFIStatuses = { id: '123' };
        rFIStatusesServiceStub.find.resolves(foundRFIStatuses);

        // WHEN
        comp.retrieveRFIStatuses('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rFIStatuses).toBe(foundRFIStatuses);
      });
    });
  });
});
