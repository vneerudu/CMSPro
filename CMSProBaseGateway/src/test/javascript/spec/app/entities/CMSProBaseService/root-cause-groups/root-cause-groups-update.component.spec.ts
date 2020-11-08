/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RootCauseGroupsUpdateComponent from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups-update.vue';
import RootCauseGroupsClass from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups-update.component';
import RootCauseGroupsService from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.service';

import RootCausesService from '@/entities/CMSProBaseService/root-causes/root-causes.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RootCauseGroups Management Update Component', () => {
    let wrapper: Wrapper<RootCauseGroupsClass>;
    let comp: RootCauseGroupsClass;
    let rootCauseGroupsServiceStub: SinonStubbedInstance<RootCauseGroupsService>;

    beforeEach(() => {
      rootCauseGroupsServiceStub = sinon.createStubInstance<RootCauseGroupsService>(RootCauseGroupsService);

      wrapper = shallowMount<RootCauseGroupsClass>(RootCauseGroupsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rootCauseGroupsService: () => rootCauseGroupsServiceStub,

          rootCausesService: () => new RootCausesService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.rootCauseGroups = entity;
        rootCauseGroupsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rootCauseGroupsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rootCauseGroups = entity;
        rootCauseGroupsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rootCauseGroupsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
