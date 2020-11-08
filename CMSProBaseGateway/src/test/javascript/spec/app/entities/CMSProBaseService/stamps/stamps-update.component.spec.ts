/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import StampsUpdateComponent from '@/entities/CMSProBaseService/stamps/stamps-update.vue';
import StampsClass from '@/entities/CMSProBaseService/stamps/stamps-update.component';
import StampsService from '@/entities/CMSProBaseService/stamps/stamps.service';

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
  describe('Stamps Management Update Component', () => {
    let wrapper: Wrapper<StampsClass>;
    let comp: StampsClass;
    let stampsServiceStub: SinonStubbedInstance<StampsService>;

    beforeEach(() => {
      stampsServiceStub = sinon.createStubInstance<StampsService>(StampsService);

      wrapper = shallowMount<StampsClass>(StampsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          stampsService: () => stampsServiceStub,

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
        comp.stamps = entity;
        stampsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stampsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stamps = entity;
        stampsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stampsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
