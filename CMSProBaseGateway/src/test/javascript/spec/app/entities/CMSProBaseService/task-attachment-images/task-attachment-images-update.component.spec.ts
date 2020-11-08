/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskAttachmentImagesUpdateComponent from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images-update.vue';
import TaskAttachmentImagesClass from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images-update.component';
import TaskAttachmentImagesService from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskAttachmentImages Management Update Component', () => {
    let wrapper: Wrapper<TaskAttachmentImagesClass>;
    let comp: TaskAttachmentImagesClass;
    let taskAttachmentImagesServiceStub: SinonStubbedInstance<TaskAttachmentImagesService>;

    beforeEach(() => {
      taskAttachmentImagesServiceStub = sinon.createStubInstance<TaskAttachmentImagesService>(TaskAttachmentImagesService);

      wrapper = shallowMount<TaskAttachmentImagesClass>(TaskAttachmentImagesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskAttachmentImagesService: () => taskAttachmentImagesServiceStub,

          tasksService: () => new TasksService(),

          rFIService: () => new RFIService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskAttachmentImages = entity;
        taskAttachmentImagesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentImagesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskAttachmentImages = entity;
        taskAttachmentImagesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentImagesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
