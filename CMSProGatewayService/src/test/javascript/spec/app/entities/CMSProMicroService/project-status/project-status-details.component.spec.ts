/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectStatusDetailComponent from '@/entities/CMSProMicroService/project-status/project-status-details.vue';
import ProjectStatusClass from '@/entities/CMSProMicroService/project-status/project-status-details.component';
import ProjectStatusService from '@/entities/CMSProMicroService/project-status/project-status.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProjectStatus Management Detail Component', () => {
    let wrapper: Wrapper<ProjectStatusClass>;
    let comp: ProjectStatusClass;
    let projectStatusServiceStub: SinonStubbedInstance<ProjectStatusService>;

    beforeEach(() => {
      projectStatusServiceStub = sinon.createStubInstance<ProjectStatusService>(ProjectStatusService);

      wrapper = shallowMount<ProjectStatusClass>(ProjectStatusDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { projectStatusService: () => projectStatusServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjectStatus = { id: 123 };
        projectStatusServiceStub.find.resolves(foundProjectStatus);

        // WHEN
        comp.retrieveProjectStatus(123);
        await comp.$nextTick();

        // THEN
        expect(comp.projectStatus).toBe(foundProjectStatus);
      });
    });
  });
});
