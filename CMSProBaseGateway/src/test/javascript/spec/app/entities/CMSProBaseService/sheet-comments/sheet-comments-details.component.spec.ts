/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SheetCommentsDetailComponent from '@/entities/CMSProBaseService/sheet-comments/sheet-comments-details.vue';
import SheetCommentsClass from '@/entities/CMSProBaseService/sheet-comments/sheet-comments-details.component';
import SheetCommentsService from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SheetComments Management Detail Component', () => {
    let wrapper: Wrapper<SheetCommentsClass>;
    let comp: SheetCommentsClass;
    let sheetCommentsServiceStub: SinonStubbedInstance<SheetCommentsService>;

    beforeEach(() => {
      sheetCommentsServiceStub = sinon.createStubInstance<SheetCommentsService>(SheetCommentsService);

      wrapper = shallowMount<SheetCommentsClass>(SheetCommentsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { sheetCommentsService: () => sheetCommentsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSheetComments = { id: '123' };
        sheetCommentsServiceStub.find.resolves(foundSheetComments);

        // WHEN
        comp.retrieveSheetComments('123');
        await comp.$nextTick();

        // THEN
        expect(comp.sheetComments).toBe(foundSheetComments);
      });
    });
  });
});
