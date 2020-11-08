/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AttachmentOthersUpdateComponent from '@/entities/CMSProBaseService/attachment-others/attachment-others-update.vue';
import AttachmentOthersClass from '@/entities/CMSProBaseService/attachment-others/attachment-others-update.component';
import AttachmentOthersService from '@/entities/CMSProBaseService/attachment-others/attachment-others.service';

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
  describe('AttachmentOthers Management Update Component', () => {
    let wrapper: Wrapper<AttachmentOthersClass>;
    let comp: AttachmentOthersClass;
    let attachmentOthersServiceStub: SinonStubbedInstance<AttachmentOthersService>;

    beforeEach(() => {
      attachmentOthersServiceStub = sinon.createStubInstance<AttachmentOthersService>(AttachmentOthersService);

      wrapper = shallowMount<AttachmentOthersClass>(AttachmentOthersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          attachmentOthersService: () => attachmentOthersServiceStub,

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
        comp.attachmentOthers = entity;
        attachmentOthersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentOthersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.attachmentOthers = entity;
        attachmentOthersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachmentOthersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
