/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SheetsUpdateComponent from '@/entities/CMSProBaseService/sheets/sheets-update.vue';
import SheetsClass from '@/entities/CMSProBaseService/sheets/sheets-update.component';
import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

import DocumentsService from '@/entities/CMSProBaseService/documents/documents.service';

import SheetTagsService from '@/entities/CMSProBaseService/sheet-tags/sheet-tags.service';

import SheetHistoryService from '@/entities/CMSProBaseService/sheet-history/sheet-history.service';

import AttachmentsService from '@/entities/CMSProBaseService/attachments/attachments.service';

import SheetCommentsService from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Sheets Management Update Component', () => {
    let wrapper: Wrapper<SheetsClass>;
    let comp: SheetsClass;
    let sheetsServiceStub: SinonStubbedInstance<SheetsService>;

    beforeEach(() => {
      sheetsServiceStub = sinon.createStubInstance<SheetsService>(SheetsService);

      wrapper = shallowMount<SheetsClass>(SheetsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          sheetsService: () => sheetsServiceStub,

          documentsService: () => new DocumentsService(),

          sheetTagsService: () => new SheetTagsService(),

          sheetHistoryService: () => new SheetHistoryService(),

          attachmentsService: () => new AttachmentsService(),

          sheetCommentsService: () => new SheetCommentsService(),

          tasksService: () => new TasksService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.sheets = entity;
        sheetsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sheets = entity;
        sheetsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sheetsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
