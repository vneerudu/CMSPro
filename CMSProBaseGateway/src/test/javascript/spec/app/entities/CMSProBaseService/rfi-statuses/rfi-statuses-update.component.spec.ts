/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RFIStatusesUpdateComponent from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses-update.vue';
import RFIStatusesClass from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses-update.component';
import RFIStatusesService from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RFIStatuses Management Update Component', () => {
    let wrapper: Wrapper<RFIStatusesClass>;
    let comp: RFIStatusesClass;
    let rFIStatusesServiceStub: SinonStubbedInstance<RFIStatusesService>;

    beforeEach(() => {
      rFIStatusesServiceStub = sinon.createStubInstance<RFIStatusesService>(RFIStatusesService);

      wrapper = shallowMount<RFIStatusesClass>(RFIStatusesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rFIStatusesService: () => rFIStatusesServiceStub,

          rFIService: () => new RFIService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rFIStatuses = entity;
        rFIStatusesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFIStatusesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rFIStatuses = entity;
        rFIStatusesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFIStatusesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
