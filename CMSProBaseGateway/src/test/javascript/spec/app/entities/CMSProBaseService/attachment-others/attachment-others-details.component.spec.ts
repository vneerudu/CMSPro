/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AttachmentOthersDetailComponent from '@/entities/CMSProBaseService/attachment-others/attachment-others-details.vue';
import AttachmentOthersClass from '@/entities/CMSProBaseService/attachment-others/attachment-others-details.component';
import AttachmentOthersService from '@/entities/CMSProBaseService/attachment-others/attachment-others.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AttachmentOthers Management Detail Component', () => {
    let wrapper: Wrapper<AttachmentOthersClass>;
    let comp: AttachmentOthersClass;
    let attachmentOthersServiceStub: SinonStubbedInstance<AttachmentOthersService>;

    beforeEach(() => {
      attachmentOthersServiceStub = sinon.createStubInstance<AttachmentOthersService>(AttachmentOthersService);

      wrapper = shallowMount<AttachmentOthersClass>(AttachmentOthersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { attachmentOthersService: () => attachmentOthersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttachmentOthers = { id: '123' };
        attachmentOthersServiceStub.find.resolves(foundAttachmentOthers);

        // WHEN
        comp.retrieveAttachmentOthers('123');
        await comp.$nextTick();

        // THEN
        expect(comp.attachmentOthers).toBe(foundAttachmentOthers);
      });
    });
  });
});
