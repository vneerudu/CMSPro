/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SheetTagsUpdateComponent from '@/entities/CMSProBaseService/sheet-tags/sheet-tags-update.vue';
import SheetTagsClass from '@/entities/CMSProBaseService/sheet-tags/sheet-tags-update.component';
import SheetTagsService from '@/entities/CMSProBaseService/sheet-tags/sheet-tags.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('SheetTags Management Update Component', () => {
    let wrapper: Wrapper<SheetTagsClass>;
    let comp: SheetTagsClass;
    let sheetTagsServiceStub: SinonStubbedInstance<SheetTagsService>;

    beforeEach(() => {
      sheetTagsServiceStub = sinon.createStubInstance<SheetTagsService>(SheetTagsService);

      wrapper = shallowMount<SheetTagsClass>(SheetTagsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          sheetTagsService: () => sheetTagsServiceStub,

          sheetsService: () => new SheetsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.sheetTags = entity;
        sheetTagsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetTagsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sheetTags = entity;
        sheetTagsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetTagsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
