/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskCommentsUpdateComponent from '@/entities/CMSProBaseService/task-comments/task-comments-update.vue';
import TaskCommentsClass from '@/entities/CMSProBaseService/task-comments/task-comments-update.component';
import TaskCommentsService from '@/entities/CMSProBaseService/task-comments/task-comments.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskComments Management Update Component', () => {
    let wrapper: Wrapper<TaskCommentsClass>;
    let comp: TaskCommentsClass;
    let taskCommentsServiceStub: SinonStubbedInstance<TaskCommentsService>;

    beforeEach(() => {
      taskCommentsServiceStub = sinon.createStubInstance<TaskCommentsService>(TaskCommentsService);

      wrapper = shallowMount<TaskCommentsClass>(TaskCommentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskCommentsService: () => taskCommentsServiceStub,

          tasksService: () => new TasksService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskComments = entity;
        taskCommentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskCommentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskComments = entity;
        taskCommentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskCommentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
