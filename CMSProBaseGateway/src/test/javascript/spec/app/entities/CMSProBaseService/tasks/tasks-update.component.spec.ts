/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TasksUpdateComponent from '@/entities/CMSProBaseService/tasks/tasks-update.vue';
import TasksClass from '@/entities/CMSProBaseService/tasks/tasks-update.component';
import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import TaskTypesService from '@/entities/CMSProBaseService/task-types/task-types.service';

import TaskStatusesService from '@/entities/CMSProBaseService/task-statuses/task-statuses.service';

import TaskLocationsService from '@/entities/CMSProBaseService/task-locations/task-locations.service';

import StampsService from '@/entities/CMSProBaseService/stamps/stamps.service';

import ListsService from '@/entities/CMSProBaseService/lists/lists.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

import RootCausesService from '@/entities/CMSProBaseService/root-causes/root-causes.service';

import UsersService from '@/entities/CMSProBaseService/users/users.service';

import TaskAttachmentImagesService from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images.service';

import TaskAttachmentOthersService from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others.service';

import TaskCommentsService from '@/entities/CMSProBaseService/task-comments/task-comments.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Tasks Management Update Component', () => {
    let wrapper: Wrapper<TasksClass>;
    let comp: TasksClass;
    let tasksServiceStub: SinonStubbedInstance<TasksService>;

    beforeEach(() => {
      tasksServiceStub = sinon.createStubInstance<TasksService>(TasksService);

      wrapper = shallowMount<TasksClass>(TasksUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          tasksService: () => tasksServiceStub,

          taskTypesService: () => new TaskTypesService(),

          taskStatusesService: () => new TaskStatusesService(),

          taskLocationsService: () => new TaskLocationsService(),

          stampsService: () => new StampsService(),

          listsService: () => new ListsService(),

          sheetsService: () => new SheetsService(),

          rootCausesService: () => new RootCausesService(),

          usersService: () => new UsersService(),

          taskAttachmentImagesService: () => new TaskAttachmentImagesService(),

          taskAttachmentOthersService: () => new TaskAttachmentOthersService(),

          taskCommentsService: () => new TaskCommentsService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.tasks = entity;
        tasksServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tasksServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tasks = entity;
        tasksServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tasksServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
