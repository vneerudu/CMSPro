/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SheetsDetailComponent from '@/entities/CMSProBaseService/sheets/sheets-details.vue';
import SheetsClass from '@/entities/CMSProBaseService/sheets/sheets-details.component';
import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Sheets Management Detail Component', () => {
    let wrapper: Wrapper<SheetsClass>;
    let comp: SheetsClass;
    let sheetsServiceStub: SinonStubbedInstance<SheetsService>;

    beforeEach(() => {
      sheetsServiceStub = sinon.createStubInstance<SheetsService>(SheetsService);

      wrapper = shallowMount<SheetsClass>(SheetsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { sheetsService: () => sheetsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSheets = { id: '123' };
        sheetsServiceStub.find.resolves(foundSheets);

        // WHEN
        comp.retrieveSheets('123');
        await comp.$nextTick();

        // THEN
        expect(comp.sheets).toBe(foundSheets);
      });
    });
  });
});
