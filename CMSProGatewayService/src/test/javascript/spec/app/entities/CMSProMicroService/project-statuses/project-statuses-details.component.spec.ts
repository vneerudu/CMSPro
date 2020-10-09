/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectStatusesDetailComponent from '@/entities/CMSProMicroService/project-statuses/project-statuses-details.vue';
import ProjectStatusesClass from '@/entities/CMSProMicroService/project-statuses/project-statuses-details.component';
import ProjectStatusesService from '@/entities/CMSProMicroService/project-statuses/project-statuses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProjectStatuses Management Detail Component', () => {
    let wrapper: Wrapper<ProjectStatusesClass>;
    let comp: ProjectStatusesClass;
    let projectStatusesServiceStub: SinonStubbedInstance<ProjectStatusesService>;

    beforeEach(() => {
      projectStatusesServiceStub = sinon.createStubInstance<ProjectStatusesService>(ProjectStatusesService);

      wrapper = shallowMount<ProjectStatusesClass>(ProjectStatusesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { projectStatusesService: () => projectStatusesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjectStatuses = { id: 123 };
        projectStatusesServiceStub.find.resolves(foundProjectStatuses);

        // WHEN
        comp.retrieveProjectStatuses(123);
        await comp.$nextTick();

        // THEN
        expect(comp.projectStatuses).toBe(foundProjectStatuses);
      });
    });
  });
});
