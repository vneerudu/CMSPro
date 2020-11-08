/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StatesDetailComponent from '@/entities/CMSProBaseService/states/states-details.vue';
import StatesClass from '@/entities/CMSProBaseService/states/states-details.component';
import StatesService from '@/entities/CMSProBaseService/states/states.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('States Management Detail Component', () => {
    let wrapper: Wrapper<StatesClass>;
    let comp: StatesClass;
    let statesServiceStub: SinonStubbedInstance<StatesService>;

    beforeEach(() => {
      statesServiceStub = sinon.createStubInstance<StatesService>(StatesService);

      wrapper = shallowMount<StatesClass>(StatesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { statesService: () => statesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStates = { id: '123' };
        statesServiceStub.find.resolves(foundStates);

        // WHEN
        comp.retrieveStates('123');
        await comp.$nextTick();

        // THEN
        expect(comp.states).toBe(foundStates);
      });
    });
  });
});
