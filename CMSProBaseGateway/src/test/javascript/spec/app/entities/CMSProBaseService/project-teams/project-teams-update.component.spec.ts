/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProjectTeamsUpdateComponent from '@/entities/CMSProBaseService/project-teams/project-teams-update.vue';
import ProjectTeamsClass from '@/entities/CMSProBaseService/project-teams/project-teams-update.component';
import ProjectTeamsService from '@/entities/CMSProBaseService/project-teams/project-teams.service';

import UsersService from '@/entities/CMSProBaseService/users/users.service';

import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProjectTeams Management Update Component', () => {
    let wrapper: Wrapper<ProjectTeamsClass>;
    let comp: ProjectTeamsClass;
    let projectTeamsServiceStub: SinonStubbedInstance<ProjectTeamsService>;

    beforeEach(() => {
      projectTeamsServiceStub = sinon.createStubInstance<ProjectTeamsService>(ProjectTeamsService);

      wrapper = shallowMount<ProjectTeamsClass>(ProjectTeamsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          projectTeamsService: () => projectTeamsServiceStub,

          usersService: () => new UsersService(),

          projectsService: () => new ProjectsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.projectTeams = entity;
        projectTeamsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectTeamsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.projectTeams = entity;
        projectTeamsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(projectTeamsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
