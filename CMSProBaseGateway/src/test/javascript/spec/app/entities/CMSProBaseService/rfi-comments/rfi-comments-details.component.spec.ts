/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RFICommentsDetailComponent from '@/entities/CMSProBaseService/rfi-comments/rfi-comments-details.vue';
import RFICommentsClass from '@/entities/CMSProBaseService/rfi-comments/rfi-comments-details.component';
import RFICommentsService from '@/entities/CMSProBaseService/rfi-comments/rfi-comments.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RFIComments Management Detail Component', () => {
    let wrapper: Wrapper<RFICommentsClass>;
    let comp: RFICommentsClass;
    let rFICommentsServiceStub: SinonStubbedInstance<RFICommentsService>;

    beforeEach(() => {
      rFICommentsServiceStub = sinon.createStubInstance<RFICommentsService>(RFICommentsService);

      wrapper = shallowMount<RFICommentsClass>(RFICommentsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rFICommentsService: () => rFICommentsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRFIComments = { id: '123' };
        rFICommentsServiceStub.find.resolves(foundRFIComments);

        // WHEN
        comp.retrieveRFIComments('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rFIComments).toBe(foundRFIComments);
      });
    });
  });
});
