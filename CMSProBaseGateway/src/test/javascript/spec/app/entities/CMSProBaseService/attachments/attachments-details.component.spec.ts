/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AttachmentsDetailComponent from '@/entities/CMSProBaseService/attachments/attachments-details.vue';
import AttachmentsClass from '@/entities/CMSProBaseService/attachments/attachments-details.component';
import AttachmentsService from '@/entities/CMSProBaseService/attachments/attachments.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Attachments Management Detail Component', () => {
    let wrapper: Wrapper<AttachmentsClass>;
    let comp: AttachmentsClass;
    let attachmentsServiceStub: SinonStubbedInstance<AttachmentsService>;

    beforeEach(() => {
      attachmentsServiceStub = sinon.createStubInstance<AttachmentsService>(AttachmentsService);

      wrapper = shallowMount<AttachmentsClass>(AttachmentsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { attachmentsService: () => attachmentsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttachments = { id: '123' };
        attachmentsServiceStub.find.resolves(foundAttachments);

        // WHEN
        comp.retrieveAttachments('123');
        await comp.$nextTick();

        // THEN
        expect(comp.attachments).toBe(foundAttachments);
      });
    });
  });
});
