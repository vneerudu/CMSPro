/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskLocationsUpdateComponent from '@/entities/CMSProBaseService/task-locations/task-locations-update.vue';
import TaskLocationsClass from '@/entities/CMSProBaseService/task-locations/task-locations-update.component';
import TaskLocationsService from '@/entities/CMSProBaseService/task-locations/task-locations.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskLocations Management Update Component', () => {
    let wrapper: Wrapper<TaskLocationsClass>;
    let comp: TaskLocationsClass;
    let taskLocationsServiceStub: SinonStubbedInstance<TaskLocationsService>;

    beforeEach(() => {
      taskLocationsServiceStub = sinon.createStubInstance<TaskLocationsService>(TaskLocationsService);

      wrapper = shallowMount<TaskLocationsClass>(TaskLocationsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskLocationsService: () => taskLocationsServiceStub,

          tasksService: () => new TasksService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskLocations = entity;
        taskLocationsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskLocationsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskLocations = entity;
        taskLocationsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskLocationsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
