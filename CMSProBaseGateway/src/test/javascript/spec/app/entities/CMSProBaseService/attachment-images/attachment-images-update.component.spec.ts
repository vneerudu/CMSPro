/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AttachmentImagesUpdateComponent from '@/entities/CMSProBaseService/attachment-images/attachment-images-update.vue';
import AttachmentImagesClass from '@/entities/CMSProBaseService/attachment-images/attachment-images-update.component';
import AttachmentImagesService from '@/entities/CMSProBaseService/attachment-images/attachment-images.service';

import AttachmentsService from '@/entities/CMSProBaseService/attachments/attachments.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AttachmentImages Management Update Component', () => {
    let wrapper: Wrapper<AttachmentImagesClass>;
    let comp: AttachmentImagesClass;
    let attachmentImagesServiceStub: SinonStubbedInstance<AttachmentImagesService>;

    beforeEach(() => {
      attachmentImagesServiceStub = sinon.createStubInstance<AttachmentImagesService>(AttachmentImagesService);

      wrapper = shallowMount<AttachmentImagesClass>(AttachmentImagesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          attachmentImagesService: () => attachmentImagesServiceStub,

          attachmentsService: () => new AttachmentsService(),

          rFIService: () => new RFIService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.attachmentImages = entity;
        attachmentImagesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentImagesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.attachmentImages = entity;
        attachmentImagesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentImagesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
