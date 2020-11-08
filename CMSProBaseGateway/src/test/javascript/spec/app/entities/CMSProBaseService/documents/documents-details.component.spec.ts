/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DocumentsDetailComponent from '@/entities/CMSProBaseService/documents/documents-details.vue';
import DocumentsClass from '@/entities/CMSProBaseService/documents/documents-details.component';
import DocumentsService from '@/entities/CMSProBaseService/documents/documents.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Documents Management Detail Component', () => {
    let wrapper: Wrapper<DocumentsClass>;
    let comp: DocumentsClass;
    let documentsServiceStub: SinonStubbedInstance<DocumentsService>;

    beforeEach(() => {
      documentsServiceStub = sinon.createStubInstance<DocumentsService>(DocumentsService);

      wrapper = shallowMount<DocumentsClass>(DocumentsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { documentsService: () => documentsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocuments = { id: '123' };
        documentsServiceStub.find.resolves(foundDocuments);

        // WHEN
        comp.retrieveDocuments('123');
        await comp.$nextTick();

        // THEN
        expect(comp.documents).toBe(foundDocuments);
      });
    });
  });
});
