/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskAttachmentImagesDetailComponent from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images-details.vue';
import TaskAttachmentImagesClass from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images-details.component';
import TaskAttachmentImagesService from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskAttachmentImages Management Detail Component', () => {
    let wrapper: Wrapper<TaskAttachmentImagesClass>;
    let comp: TaskAttachmentImagesClass;
    let taskAttachmentImagesServiceStub: SinonStubbedInstance<TaskAttachmentImagesService>;

    beforeEach(() => {
      taskAttachmentImagesServiceStub = sinon.createStubInstance<TaskAttachmentImagesService>(TaskAttachmentImagesService);

      wrapper = shallowMount<TaskAttachmentImagesClass>(TaskAttachmentImagesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { taskAttachmentImagesService: () => taskAttachmentImagesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskAttachmentImages = { id: '123' };
        taskAttachmentImagesServiceStub.find.resolves(foundTaskAttachmentImages);

        // WHEN
        comp.retrieveTaskAttachmentImages('123');
        await comp.$nextTick();

        // THEN
        expect(comp.taskAttachmentImages).toBe(foundTaskAttachmentImages);
      });
    });
  });
});
