/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SheetHistoryDetailComponent from '@/entities/CMSProBaseService/sheet-history/sheet-history-details.vue';
import SheetHistoryClass from '@/entities/CMSProBaseService/sheet-history/sheet-history-details.component';
import SheetHistoryService from '@/entities/CMSProBaseService/sheet-history/sheet-history.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SheetHistory Management Detail Component', () => {
    let wrapper: Wrapper<SheetHistoryClass>;
    let comp: SheetHistoryClass;
    let sheetHistoryServiceStub: SinonStubbedInstance<SheetHistoryService>;

    beforeEach(() => {
      sheetHistoryServiceStub = sinon.createStubInstance<SheetHistoryService>(SheetHistoryService);

      wrapper = shallowMount<SheetHistoryClass>(SheetHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { sheetHistoryService: () => sheetHistoryServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSheetHistory = { id: '123' };
        sheetHistoryServiceStub.find.resolves(foundSheetHistory);

        // WHEN
        comp.retrieveSheetHistory('123');
        await comp.$nextTick();

        // THEN
        expect(comp.sheetHistory).toBe(foundSheetHistory);
      });
    });
  });
});
