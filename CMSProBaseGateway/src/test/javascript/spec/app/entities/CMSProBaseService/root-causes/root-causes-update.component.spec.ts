/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RootCausesUpdateComponent from '@/entities/CMSProBaseService/root-causes/root-causes-update.vue';
import RootCausesClass from '@/entities/CMSProBaseService/root-causes/root-causes-update.component';
import RootCausesService from '@/entities/CMSProBaseService/root-causes/root-causes.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import RootCauseGroupsService from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RootCauses Management Update Component', () => {
    let wrapper: Wrapper<RootCausesClass>;
    let comp: RootCausesClass;
    let rootCausesServiceStub: SinonStubbedInstance<RootCausesService>;

    beforeEach(() => {
      rootCausesServiceStub = sinon.createStubInstance<RootCausesService>(RootCausesService);

      wrapper = shallowMount<RootCausesClass>(RootCausesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rootCausesService: () => rootCausesServiceStub,

          tasksService: () => new TasksService(),

          rootCauseGroupsService: () => new RootCauseGroupsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rootCauses = entity;
        rootCausesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rootCausesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rootCauses = entity;
        rootCausesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rootCausesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
