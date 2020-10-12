/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProjectStatusUpdateComponent from '@/entities/CMSProMicroService/project-status/project-status-update.vue';
import ProjectStatusClass from '@/entities/CMSProMicroService/project-status/project-status-update.component';
import ProjectStatusService from '@/entities/CMSProMicroService/project-status/project-status.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProjectStatus Management Update Component', () => {
    let wrapper: Wrapper<ProjectStatusClass>;
    let comp: ProjectStatusClass;
    let projectStatusServiceStub: SinonStubbedInstance<ProjectStatusService>;

    beforeEach(() => {
      projectStatusServiceStub = sinon.createStubInstance<ProjectStatusService>(ProjectStatusService);

      wrapper = shallowMount<ProjectStatusClass>(ProjectStatusUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          projectStatusService: () => projectStatusServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.projectStatus = entity;
        projectStatusServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectStatusServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.projectStatus = entity;
        projectStatusServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectStatusServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
