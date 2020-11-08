/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RFITimeLineUpdateComponent from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line-update.vue';
import RFITimeLineClass from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line-update.component';
import RFITimeLineService from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RFITimeLine Management Update Component', () => {
    let wrapper: Wrapper<RFITimeLineClass>;
    let comp: RFITimeLineClass;
    let rFITimeLineServiceStub: SinonStubbedInstance<RFITimeLineService>;

    beforeEach(() => {
      rFITimeLineServiceStub = sinon.createStubInstance<RFITimeLineService>(RFITimeLineService);

      wrapper = shallowMount<RFITimeLineClass>(RFITimeLineUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rFITimeLineService: () => rFITimeLineServiceStub,

          rFIService: () => new RFIService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rFITimeLine = entity;
        rFITimeLineServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFITimeLineServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rFITimeLine = entity;
        rFITimeLineServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFITimeLineServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
