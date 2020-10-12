/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectsDetailComponent from '@/entities/CMSProMicroService/projects/projects-details.vue';
import ProjectsClass from '@/entities/CMSProMicroService/projects/projects-details.component';
import ProjectsService from '@/entities/CMSProMicroService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Projects Management Detail Component', () => {
    let wrapper: Wrapper<ProjectsClass>;
    let comp: ProjectsClass;
    let projectsServiceStub: SinonStubbedInstance<ProjectsService>;

    beforeEach(() => {
      projectsServiceStub = sinon.createStubInstance<ProjectsService>(ProjectsService);

      wrapper = shallowMount<ProjectsClass>(ProjectsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { projectsService: () => projectsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjects = { id: 123 };
        projectsServiceStub.find.resolves(foundProjects);

        // WHEN
        comp.retrieveProjects(123);
        await comp.$nextTick();

        // THEN
        expect(comp.projects).toBe(foundProjects);
      });
    });
  });
});
