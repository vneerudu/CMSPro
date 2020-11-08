/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProjectTeamsDetailComponent from '@/entities/CMSProBaseService/project-teams/project-teams-details.vue';
import ProjectTeamsClass from '@/entities/CMSProBaseService/project-teams/project-teams-details.component';
import ProjectTeamsService from '@/entities/CMSProBaseService/project-teams/project-teams.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProjectTeams Management Detail Component', () => {
    let wrapper: Wrapper<ProjectTeamsClass>;
    let comp: ProjectTeamsClass;
    let projectTeamsServiceStub: SinonStubbedInstance<ProjectTeamsService>;

    beforeEach(() => {
      projectTeamsServiceStub = sinon.createStubInstance<ProjectTeamsService>(ProjectTeamsService);

      wrapper = shallowMount<ProjectTeamsClass>(ProjectTeamsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { projectTeamsService: () => projectTeamsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProjectTeams = { id: '123' };
        projectTeamsServiceStub.find.resolves(foundProjectTeams);

        // WHEN
        comp.retrieveProjectTeams('123');
        await comp.$nextTick();

        // THEN
        expect(comp.projectTeams).toBe(foundProjectTeams);
      });
    });
  });
});
