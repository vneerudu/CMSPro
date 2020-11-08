/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskAttachmentOthersDetailComponent from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others-details.vue';
import TaskAttachmentOthersClass from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others-details.component';
import TaskAttachmentOthersService from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskAttachmentOthers Management Detail Component', () => {
    let wrapper: Wrapper<TaskAttachmentOthersClass>;
    let comp: TaskAttachmentOthersClass;
    let taskAttachmentOthersServiceStub: SinonStubbedInstance<TaskAttachmentOthersService>;

    beforeEach(() => {
      taskAttachmentOthersServiceStub = sinon.createStubInstance<TaskAttachmentOthersService>(TaskAttachmentOthersService);

      wrapper = shallowMount<TaskAttachmentOthersClass>(TaskAttachmentOthersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { taskAttachmentOthersService: () => taskAttachmentOthersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskAttachmentOthers = { id: '123' };
        taskAttachmentOthersServiceStub.find.resolves(foundTaskAttachmentOthers);

        // WHEN
        comp.retrieveTaskAttachmentOthers('123');
        await comp.$nextTick();

        // THEN
        expect(comp.taskAttachmentOthers).toBe(foundTaskAttachmentOthers);
      });
    });
  });
});
