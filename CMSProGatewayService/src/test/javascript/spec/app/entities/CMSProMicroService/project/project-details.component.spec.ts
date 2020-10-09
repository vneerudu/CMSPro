/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectDetailComponent from '@/entities/CMSProMicroService/project/project-details.vue';
import ProjectClass from '@/entities/CMSProMicroService/project/project-details.component';
import ProjectService from '@/entities/CMSProMicroService/project/project.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Project Management Detail Component', () => {
    let wrapper: Wrapper<ProjectClass>;
    let comp: ProjectClass;
    let projectServiceStub: SinonStubbedInstance<ProjectService>;

    beforeEach(() => {
      projectServiceStub = sinon.createStubInstance<ProjectService>(ProjectService);

      wrapper = shallowMount<ProjectClass>(ProjectDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { projectService: () => projectServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProject = { id: 123 };
        projectServiceStub.find.resolves(foundProject);

        // WHEN
        comp.retrieveProject(123);
        await comp.$nextTick();

        // THEN
        expect(comp.project).toBe(foundProject);
      });
    });
  });
});
