/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProjectsUpdateComponent from '@/entities/CMSProBaseService/projects/projects-update.vue';
import ProjectsClass from '@/entities/CMSProBaseService/projects/projects-update.component';
import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';

import AddressesService from '@/entities/CMSProBaseService/addresses/addresses.service';

import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

import ProjectTeamsService from '@/entities/CMSProBaseService/project-teams/project-teams.service';

import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';

import StampsService from '@/entities/CMSProBaseService/stamps/stamps.service';

import ListsService from '@/entities/CMSProBaseService/lists/lists.service';

import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';

import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';

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

          addressesService: () => new AddressesService(),

          sheetsService: () => new SheetsService(),

          projectTeamsService: () => new ProjectTeamsService(),

          tasksService: () => new TasksService(),

          stampsService: () => new StampsService(),

          listsService: () => new ListsService(),

          rFIService: () => new RFIService(),

          accountsService: () => new AccountsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
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
