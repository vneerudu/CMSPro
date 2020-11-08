/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TasksDetailComponent from '@/entities/CMSProBaseService/tasks/tasks-details.vue';
import TasksClass from '@/entities/CMSProBaseService/tasks/tasks-details.component';
import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Tasks Management Detail Component', () => {
    let wrapper: Wrapper<TasksClass>;
    let comp: TasksClass;
    let tasksServiceStub: SinonStubbedInstance<TasksService>;

    beforeEach(() => {
      tasksServiceStub = sinon.createStubInstance<TasksService>(TasksService);

      wrapper = shallowMount<TasksClass>(TasksDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { tasksService: () => tasksServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTasks = { id: '123' };
        tasksServiceStub.find.resolves(foundTasks);

        // WHEN
        comp.retrieveTasks('123');
        await comp.$nextTick();

        // THEN
        expect(comp.tasks).toBe(foundTasks);
      });
    });
  });
});
