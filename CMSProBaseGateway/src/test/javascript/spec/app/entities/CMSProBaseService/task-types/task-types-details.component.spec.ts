/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskTypesDetailComponent from '@/entities/CMSProBaseService/task-types/task-types-details.vue';
import TaskTypesClass from '@/entities/CMSProBaseService/task-types/task-types-details.component';
import TaskTypesService from '@/entities/CMSProBaseService/task-types/task-types.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskTypes Management Detail Component', () => {
    let wrapper: Wrapper<TaskTypesClass>;
    let comp: TaskTypesClass;
    let taskTypesServiceStub: SinonStubbedInstance<TaskTypesService>;

    beforeEach(() => {
      taskTypesServiceStub = sinon.createStubInstance<TaskTypesService>(TaskTypesService);

      wrapper = shallowMount<TaskTypesClass>(TaskTypesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { taskTypesService: () => taskTypesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskTypes = { id: '123' };
        taskTypesServiceStub.find.resolves(foundTaskTypes);

        // WHEN
        comp.retrieveTaskTypes('123');
        await comp.$nextTick();

        // THEN
        expect(comp.taskTypes).toBe(foundTaskTypes);
      });
    });
  });
});
