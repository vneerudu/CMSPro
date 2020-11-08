/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskCommentsDetailComponent from '@/entities/CMSProBaseService/task-comments/task-comments-details.vue';
import TaskCommentsClass from '@/entities/CMSProBaseService/task-comments/task-comments-details.component';
import TaskCommentsService from '@/entities/CMSProBaseService/task-comments/task-comments.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskComments Management Detail Component', () => {
    let wrapper: Wrapper<TaskCommentsClass>;
    let comp: TaskCommentsClass;
    let taskCommentsServiceStub: SinonStubbedInstance<TaskCommentsService>;

    beforeEach(() => {
      taskCommentsServiceStub = sinon.createStubInstance<TaskCommentsService>(TaskCommentsService);

      wrapper = shallowMount<TaskCommentsClass>(TaskCommentsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { taskCommentsService: () => taskCommentsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskComments = { id: '123' };
        taskCommentsServiceStub.find.resolves(foundTaskComments);

        // WHEN
        comp.retrieveTaskComments('123');
        await comp.$nextTick();

        // THEN
        expect(comp.taskComments).toBe(foundTaskComments);
      });
    });
  });
});
