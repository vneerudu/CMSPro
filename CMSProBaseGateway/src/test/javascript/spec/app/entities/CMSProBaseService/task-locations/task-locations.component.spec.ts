/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TaskLocationsComponent from '@/entities/CMSProBaseService/task-locations/task-locations.vue';
import TaskLocationsClass from '@/entities/CMSProBaseService/task-locations/task-locations.component';
import TaskLocationsService from '@/entities/CMSProBaseService/task-locations/task-locations.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('TaskLocations Management Component', () => {
    let wrapper: Wrapper<TaskLocationsClass>;
    let comp: TaskLocationsClass;
    let taskLocationsServiceStub: SinonStubbedInstance<TaskLocationsService>;

    beforeEach(() => {
      taskLocationsServiceStub = sinon.createStubInstance<TaskLocationsService>(TaskLocationsService);
      taskLocationsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TaskLocationsClass>(TaskLocationsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          taskLocationsService: () => taskLocationsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      taskLocationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.retrieveAllTaskLocationss();
      await comp.$nextTick();

      // THEN
      expect(taskLocationsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.taskLocations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', async () => {
      // GIVEN
      taskLocationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(taskLocationsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.taskLocations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      taskLocationsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(taskLocationsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      taskLocationsServiceStub.retrieve.reset();
      taskLocationsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(taskLocationsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.taskLocations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      taskLocationsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '123' });
      comp.removeTaskLocations();
      await comp.$nextTick();

      // THEN
      expect(taskLocationsServiceStub.delete.called).toBeTruthy();
      expect(taskLocationsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
