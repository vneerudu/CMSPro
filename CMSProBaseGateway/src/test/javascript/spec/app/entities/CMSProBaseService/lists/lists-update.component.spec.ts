/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ListsUpdateComponent from '@/entities/CMSProBaseService/lists/lists-update.vue';
import ListsClass from '@/entities/CMSProBaseService/lists/lists-update.component';
import ListsService from '@/entities/CMSProBaseService/lists/lists.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Lists Management Update Component', () => {
    let wrapper: Wrapper<ListsClass>;
    let comp: ListsClass;
    let listsServiceStub: SinonStubbedInstance<ListsService>;

    beforeEach(() => {
      listsServiceStub = sinon.createStubInstance<ListsService>(ListsService);

      wrapper = shallowMount<ListsClass>(ListsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          listsService: () => listsServiceStub,

          tasksService: () => new TasksService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.lists = entity;
        listsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.lists = entity;
        listsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(listsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
