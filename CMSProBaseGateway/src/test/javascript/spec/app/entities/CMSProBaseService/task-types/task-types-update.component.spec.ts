/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskTypesUpdateComponent from '@/entities/CMSProBaseService/task-types/task-types-update.vue';
import TaskTypesClass from '@/entities/CMSProBaseService/task-types/task-types-update.component';
import TaskTypesService from '@/entities/CMSProBaseService/task-types/task-types.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskTypes Management Update Component', () => {
    let wrapper: Wrapper<TaskTypesClass>;
    let comp: TaskTypesClass;
    let taskTypesServiceStub: SinonStubbedInstance<TaskTypesService>;

    beforeEach(() => {
      taskTypesServiceStub = sinon.createStubInstance<TaskTypesService>(TaskTypesService);

      wrapper = shallowMount<TaskTypesClass>(TaskTypesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskTypesService: () => taskTypesServiceStub,

          tasksService: () => new TasksService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskTypes = entity;
        taskTypesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskTypesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskTypes = entity;
        taskTypesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskTypesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
