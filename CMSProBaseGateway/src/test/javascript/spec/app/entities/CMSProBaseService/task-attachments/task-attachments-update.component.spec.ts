/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskAttachmentsUpdateComponent from '@/entities/CMSProBaseService/task-attachments/task-attachments-update.vue';
import TaskAttachmentsClass from '@/entities/CMSProBaseService/task-attachments/task-attachments-update.component';
import TaskAttachmentsService from '@/entities/CMSProBaseService/task-attachments/task-attachments.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('TaskAttachments Management Update Component', () => {
    let wrapper: Wrapper<TaskAttachmentsClass>;
    let comp: TaskAttachmentsClass;
    let taskAttachmentsServiceStub: SinonStubbedInstance<TaskAttachmentsService>;

    beforeEach(() => {
      taskAttachmentsServiceStub = sinon.createStubInstance<TaskAttachmentsService>(TaskAttachmentsService);

      wrapper = shallowMount<TaskAttachmentsClass>(TaskAttachmentsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          taskAttachmentsService: () => taskAttachmentsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.taskAttachments = entity;
        taskAttachmentsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taskAttachments = entity;
        taskAttachmentsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(taskAttachmentsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
