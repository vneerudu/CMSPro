/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskStatusesDetailComponent from '@/entities/CMSProBaseService/task-statuses/task-statuses-details.vue';
import TaskStatusesClass from '@/entities/CMSProBaseService/task-statuses/task-statuses-details.component';
import TaskStatusesService from '@/entities/CMSProBaseService/task-statuses/task-statuses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskStatuses Management Detail Component', () => {
    let wrapper: Wrapper<TaskStatusesClass>;
    let comp: TaskStatusesClass;
    let taskStatusesServiceStub: SinonStubbedInstance<TaskStatusesService>;

    beforeEach(() => {
      taskStatusesServiceStub = sinon.createStubInstance<TaskStatusesService>(TaskStatusesService);

      wrapper = shallowMount<TaskStatusesClass>(TaskStatusesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { taskStatusesService: () => taskStatusesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskStatuses = { id: '123' };
        taskStatusesServiceStub.find.resolves(foundTaskStatuses);

        // WHEN
        comp.retrieveTaskStatuses('123');
        await comp.$nextTick();

        // THEN
        expect(comp.taskStatuses).toBe(foundTaskStatuses);
      });
    });
  });
});
