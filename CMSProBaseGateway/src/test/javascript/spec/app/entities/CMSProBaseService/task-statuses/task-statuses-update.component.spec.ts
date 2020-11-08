/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskStatusesUpdateComponent from '@/entities/CMSProBaseService/task-statuses/task-statuses-update.vue';
import TaskStatusesClass from '@/entities/CMSProBaseService/task-statuses/task-statuses-update.component';
import TaskStatusesService from '@/entities/CMSProBaseService/task-statuses/task-statuses.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskStatuses Management Update Component', () => {
    let wrapper: Wrapper<TaskStatusesClass>;
    let comp: TaskStatusesClass;
    let taskStatusesServiceStub: SinonStubbedInstance<TaskStatusesService>;

    beforeEach(() => {
      taskStatusesServiceStub = sinon.createStubInstance<TaskStatusesService>(TaskStatusesService);

      wrapper = shallowMount<TaskStatusesClass>(TaskStatusesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskStatusesService: () => taskStatusesServiceStub,

          tasksService: () => new TasksService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskStatuses = entity;
        taskStatusesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskStatusesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskStatuses = entity;
        taskStatusesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskStatusesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
