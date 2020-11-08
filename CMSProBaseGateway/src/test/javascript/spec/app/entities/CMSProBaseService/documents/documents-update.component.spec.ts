/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DocumentsUpdateComponent from '@/entities/CMSProBaseService/documents/documents-update.vue';
import DocumentsClass from '@/entities/CMSProBaseService/documents/documents-update.component';
import DocumentsService from '@/entities/CMSProBaseService/documents/documents.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Documents Management Update Component', () => {
    let wrapper: Wrapper<DocumentsClass>;
    let comp: DocumentsClass;
    let documentsServiceStub: SinonStubbedInstance<DocumentsService>;

    beforeEach(() => {
      documentsServiceStub = sinon.createStubInstance<DocumentsService>(DocumentsService);

      wrapper = shallowMount<DocumentsClass>(DocumentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          documentsService: () => documentsServiceStub,

          sheetsService: () => new SheetsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.documents = entity;
        documentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documents = entity;
        documentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
