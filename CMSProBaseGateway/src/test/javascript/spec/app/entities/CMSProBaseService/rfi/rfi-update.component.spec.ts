/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RFIUpdateComponent from '@/entities/CMSProBaseService/rfi/rfi-update.vue';
import RFIClass from '@/entities/CMSProBaseService/rfi/rfi-update.component';
import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

import RFIStatusesService from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses.service';

import TaskAttachmentImagesService from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images.service';

import TaskAttachmentOthersService from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others.service';

import AttachmentOthersService from '@/entities/CMSProBaseService/attachment-others/attachment-others.service';

import AttachmentImagesService from '@/entities/CMSProBaseService/attachment-images/attachment-images.service';

import RFICommentsService from '@/entities/CMSProBaseService/rfi-comments/rfi-comments.service';

import RFITimeLineService from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RFI Management Update Component', () => {
    let wrapper: Wrapper<RFIClass>;
    let comp: RFIClass;
    let rFIServiceStub: SinonStubbedInstance<RFIService>;

    beforeEach(() => {
      rFIServiceStub = sinon.createStubInstance<RFIService>(RFIService);

      wrapper = shallowMount<RFIClass>(RFIUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rFIService: () => rFIServiceStub,

          rFIStatusesService: () => new RFIStatusesService(),

          taskAttachmentImagesService: () => new TaskAttachmentImagesService(),

          taskAttachmentOthersService: () => new TaskAttachmentOthersService(),

          attachmentOthersService: () => new AttachmentOthersService(),

          attachmentImagesService: () => new AttachmentImagesService(),

          rFICommentsService: () => new RFICommentsService(),

          rFITimeLineService: () => new RFITimeLineService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rFI = entity;
        rFIServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFIServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rFI = entity;
        rFIServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rFIServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
