/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SheetTagsDetailComponent from '@/entities/CMSProBaseService/sheet-tags/sheet-tags-details.vue';
import SheetTagsClass from '@/entities/CMSProBaseService/sheet-tags/sheet-tags-details.component';
import SheetTagsService from '@/entities/CMSProBaseService/sheet-tags/sheet-tags.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SheetTags Management Detail Component', () => {
    let wrapper: Wrapper<SheetTagsClass>;
    let comp: SheetTagsClass;
    let sheetTagsServiceStub: SinonStubbedInstance<SheetTagsService>;

    beforeEach(() => {
      sheetTagsServiceStub = sinon.createStubInstance<SheetTagsService>(SheetTagsService);

      wrapper = shallowMount<SheetTagsClass>(SheetTagsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { sheetTagsService: () => sheetTagsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSheetTags = { id: '123' };
        sheetTagsServiceStub.find.resolves(foundSheetTags);

        // WHEN
        comp.retrieveSheetTags('123');
        await comp.$nextTick();

        // THEN
        expect(comp.sheetTags).toBe(foundSheetTags);
      });
    });
  });
});
