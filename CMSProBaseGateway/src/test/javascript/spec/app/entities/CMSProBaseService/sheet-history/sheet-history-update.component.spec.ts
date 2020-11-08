/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SheetHistoryUpdateComponent from '@/entities/CMSProBaseService/sheet-history/sheet-history-update.vue';
import SheetHistoryClass from '@/entities/CMSProBaseService/sheet-history/sheet-history-update.component';
import SheetHistoryService from '@/entities/CMSProBaseService/sheet-history/sheet-history.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SheetHistory Management Update Component', () => {
    let wrapper: Wrapper<SheetHistoryClass>;
    let comp: SheetHistoryClass;
    let sheetHistoryServiceStub: SinonStubbedInstance<SheetHistoryService>;

    beforeEach(() => {
      sheetHistoryServiceStub = sinon.createStubInstance<SheetHistoryService>(SheetHistoryService);

      wrapper = shallowMount<SheetHistoryClass>(SheetHistoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          sheetHistoryService: () => sheetHistoryServiceStub,

          sheetsService: () => new SheetsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.sheetHistory = entity;
        sheetHistoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetHistoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sheetHistory = entity;
        sheetHistoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetHistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
