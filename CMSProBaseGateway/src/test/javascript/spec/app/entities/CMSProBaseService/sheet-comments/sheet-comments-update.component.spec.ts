/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SheetCommentsUpdateComponent from '@/entities/CMSProBaseService/sheet-comments/sheet-comments-update.vue';
import SheetCommentsClass from '@/entities/CMSProBaseService/sheet-comments/sheet-comments-update.component';
import SheetCommentsService from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SheetComments Management Update Component', () => {
    let wrapper: Wrapper<SheetCommentsClass>;
    let comp: SheetCommentsClass;
    let sheetCommentsServiceStub: SinonStubbedInstance<SheetCommentsService>;

    beforeEach(() => {
      sheetCommentsServiceStub = sinon.createStubInstance<SheetCommentsService>(SheetCommentsService);

      wrapper = shallowMount<SheetCommentsClass>(SheetCommentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          sheetCommentsService: () => sheetCommentsServiceStub,

          sheetsService: () => new SheetsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.sheetComments = entity;
        sheetCommentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetCommentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sheetComments = entity;
        sheetCommentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetCommentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
