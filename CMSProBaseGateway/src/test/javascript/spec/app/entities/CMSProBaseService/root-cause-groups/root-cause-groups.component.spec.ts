/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RootCauseGroupsComponent from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.vue';
import RootCauseGroupsClass from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.component';
import RootCauseGroupsService from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.service';

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
  describe('RootCauseGroups Management Component', () => {
    let wrapper: Wrapper<RootCauseGroupsClass>;
    let comp: RootCauseGroupsClass;
    let rootCauseGroupsServiceStub: SinonStubbedInstance<RootCauseGroupsService>;

    beforeEach(() => {
      rootCauseGroupsServiceStub = sinon.createStubInstance<RootCauseGroupsService>(RootCauseGroupsService);
      rootCauseGroupsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RootCauseGroupsClass>(RootCauseGroupsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          rootCauseGroupsService: () => rootCauseGroupsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      rootCauseGroupsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.retrieveAllRootCauseGroupss();
      await comp.$nextTick();

      // THEN
      expect(rootCauseGroupsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.rootCauseGroups[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', async () => {
      // GIVEN
      rootCauseGroupsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(rootCauseGroupsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.rootCauseGroups[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      rootCauseGroupsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(rootCauseGroupsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      rootCauseGroupsServiceStub.retrieve.reset();
      rootCauseGroupsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(rootCauseGroupsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.rootCauseGroups[0]).toEqual(jasmine.objectContaining({ id: '123' }));
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
      rootCauseGroupsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '123' });
      comp.removeRootCauseGroups();
      await comp.$nextTick();

      // THEN
      expect(rootCauseGroupsServiceStub.delete.called).toBeTruthy();
      expect(rootCauseGroupsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
