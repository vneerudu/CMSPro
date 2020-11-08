/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ListsDetailComponent from '@/entities/CMSProBaseService/lists/lists-details.vue';
import ListsClass from '@/entities/CMSProBaseService/lists/lists-details.component';
import ListsService from '@/entities/CMSProBaseService/lists/lists.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Lists Management Detail Component', () => {
    let wrapper: Wrapper<ListsClass>;
    let comp: ListsClass;
    let listsServiceStub: SinonStubbedInstance<ListsService>;

    beforeEach(() => {
      listsServiceStub = sinon.createStubInstance<ListsService>(ListsService);

      wrapper = shallowMount<ListsClass>(ListsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { listsService: () => listsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLists = { id: '123' };
        listsServiceStub.find.resolves(foundLists);

        // WHEN
        comp.retrieveLists('123');
        await comp.$nextTick();

        // THEN
        expect(comp.lists).toBe(foundLists);
      });
    });
  });
});
