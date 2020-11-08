/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RootCauseGroupsDetailComponent from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups-details.vue';
import RootCauseGroupsClass from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups-details.component';
import RootCauseGroupsService from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RootCauseGroups Management Detail Component', () => {
    let wrapper: Wrapper<RootCauseGroupsClass>;
    let comp: RootCauseGroupsClass;
    let rootCauseGroupsServiceStub: SinonStubbedInstance<RootCauseGroupsService>;

    beforeEach(() => {
      rootCauseGroupsServiceStub = sinon.createStubInstance<RootCauseGroupsService>(RootCauseGroupsService);

      wrapper = shallowMount<RootCauseGroupsClass>(RootCauseGroupsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rootCauseGroupsService: () => rootCauseGroupsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRootCauseGroups = { id: '123' };
        rootCauseGroupsServiceStub.find.resolves(foundRootCauseGroups);

        // WHEN
        comp.retrieveRootCauseGroups('123');
        await comp.$nextTick();

        // THEN
        expect(comp.rootCauseGroups).toBe(foundRootCauseGroups);
      });
    });
  });
});
