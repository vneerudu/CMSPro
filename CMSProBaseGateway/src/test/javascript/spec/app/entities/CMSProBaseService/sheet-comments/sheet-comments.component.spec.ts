/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SheetCommentsComponent from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.vue';
import SheetCommentsClass from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.component';
import SheetCommentsService from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.service';

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
  describe('SheetComments Management Component', () => {
    let wrapper: Wrapper<SheetCommentsClass>;
    let comp: SheetCommentsClass;
    let sheetCommentsServiceStub: SinonStubbedInstance<SheetCommentsService>;

    beforeEach(() => {
      sheetCommentsServiceStub = sinon.createStubInstance<SheetCommentsService>(SheetCommentsService);
      sheetCommentsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SheetCommentsClass>(SheetCommentsComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          sheetCommentsService: () => sheetCommentsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sheetCommentsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.retrieveAllSheetCommentss();
      await comp.$nextTick();

      // THEN
      expect(sheetCommentsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sheetComments[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should load a page', async () => {
      // GIVEN
      sheetCommentsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(sheetCommentsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sheetComments[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      sheetCommentsServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(sheetCommentsServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      sheetCommentsServiceStub.retrieve.reset();
      sheetCommentsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '123' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(sheetCommentsServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.sheetComments[0]).toEqual(jasmine.objectContaining({ id: '123' }));
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
      sheetCommentsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '123' });
      comp.removeSheetComments();
      await comp.$nextTick();

      // THEN
      expect(sheetCommentsServiceStub.delete.called).toBeTruthy();
      expect(sheetCommentsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
