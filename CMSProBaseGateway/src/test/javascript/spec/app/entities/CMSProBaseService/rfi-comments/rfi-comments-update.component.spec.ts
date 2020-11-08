/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RFICommentsUpdateComponent from '@/entities/CMSProBaseService/rfi-comments/rfi-comments-update.vue';
import RFICommentsClass from '@/entities/CMSProBaseService/rfi-comments/rfi-comments-update.component';
import RFICommentsService from '@/entities/CMSProBaseService/rfi-comments/rfi-comments.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RFIComments Management Update Component', () => {
    let wrapper: Wrapper<RFICommentsClass>;
    let comp: RFICommentsClass;
    let rFICommentsServiceStub: SinonStubbedInstance<RFICommentsService>;

    beforeEach(() => {
      rFICommentsServiceStub = sinon.createStubInstance<RFICommentsService>(RFICommentsService);

      wrapper = shallowMount<RFICommentsClass>(RFICommentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rFICommentsService: () => rFICommentsServiceStub,

          rFIService: () => new RFIService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rFIComments = entity;
        rFICommentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFICommentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rFIComments = entity;
        rFICommentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFICommentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
