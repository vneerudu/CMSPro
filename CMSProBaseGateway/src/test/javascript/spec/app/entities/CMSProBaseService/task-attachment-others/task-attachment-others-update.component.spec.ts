/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskAttachmentOthersUpdateComponent from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others-update.vue';
import TaskAttachmentOthersClass from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others-update.component';
import TaskAttachmentOthersService from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others.service';

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
  describe('TaskAttachmentOthers Management Update Component', () => {
    let wrapper: Wrapper<TaskAttachmentOthersClass>;
    let comp: TaskAttachmentOthersClass;
    let taskAttachmentOthersServiceStub: SinonStubbedInstance<TaskAttachmentOthersService>;

    beforeEach(() => {
      taskAttachmentOthersServiceStub = sinon.createStubInstance<TaskAttachmentOthersService>(TaskAttachmentOthersService);

      wrapper = shallowMount<TaskAttachmentOthersClass>(TaskAttachmentOthersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskAttachmentOthersService: () => taskAttachmentOthersServiceStub,

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
        comp.taskAttachmentOthers = entity;
        taskAttachmentOthersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentOthersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskAttachmentOthers = entity;
        taskAttachmentOthersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentOthersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
