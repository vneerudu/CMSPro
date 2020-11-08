/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AttachmentsUpdateComponent from '@/entities/CMSProBaseService/attachments/attachments-update.vue';
import AttachmentsClass from '@/entities/CMSProBaseService/attachments/attachments-update.component';
import AttachmentsService from '@/entities/CMSProBaseService/attachments/attachments.service';

import AttachmentImagesService from '@/entities/CMSProBaseService/attachment-images/attachment-images.service';

import AttachmentOthersService from '@/entities/CMSProBaseService/attachment-others/attachment-others.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Attachments Management Update Component', () => {
    let wrapper: Wrapper<AttachmentsClass>;
    let comp: AttachmentsClass;
    let attachmentsServiceStub: SinonStubbedInstance<AttachmentsService>;

    beforeEach(() => {
      attachmentsServiceStub = sinon.createStubInstance<AttachmentsService>(AttachmentsService);

      wrapper = shallowMount<AttachmentsClass>(AttachmentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          attachmentsService: () => attachmentsServiceStub,

          attachmentImagesService: () => new AttachmentImagesService(),

          attachmentOthersService: () => new AttachmentOthersService(),

          sheetsService: () => new SheetsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.attachments = entity;
        attachmentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.attachments = entity;
        attachmentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
