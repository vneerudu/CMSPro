/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProjectsUpdateComponent from '@/entities/CMSProMicroService/projects/projects-update.vue';
import ProjectsClass from '@/entities/CMSProMicroService/projects/projects-update.component';
import ProjectsService from '@/entities/CMSProMicroService/projects/projects.service';

import ProjectStatusService from '@/entities/CMSProMicroService/project-status/project-status.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Projects Management Update Component', () => {
    let wrapper: Wrapper<ProjectsClass>;
    let comp: ProjectsClass;
    let projectsServiceStub: SinonStubbedInstance<ProjectsService>;

    beforeEach(() => {
      projectsServiceStub = sinon.createStubInstance<ProjectsService>(ProjectsService);

      wrapper = shallowMount<ProjectsClass>(ProjectsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          projectsService: () => projectsServiceStub,

          projectStatusService: () => new ProjectStatusService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.projects = entity;
        projectsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.projects = entity;
        projectsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
