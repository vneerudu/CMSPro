/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AttachmentImagesDetailComponent from '@/entities/CMSProBaseService/attachment-images/attachment-images-details.vue';
import AttachmentImagesClass from '@/entities/CMSProBaseService/attachment-images/attachment-images-details.component';
import AttachmentImagesService from '@/entities/CMSProBaseService/attachment-images/attachment-images.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AttachmentImages Management Detail Component', () => {
    let wrapper: Wrapper<AttachmentImagesClass>;
    let comp: AttachmentImagesClass;
    let attachmentImagesServiceStub: SinonStubbedInstance<AttachmentImagesService>;

    beforeEach(() => {
      attachmentImagesServiceStub = sinon.createStubInstance<AttachmentImagesService>(AttachmentImagesService);

      wrapper = shallowMount<AttachmentImagesClass>(AttachmentImagesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { attachmentImagesService: () => attachmentImagesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttachmentImages = { id: '123' };
        attachmentImagesServiceStub.find.resolves(foundAttachmentImages);

        // WHEN
        comp.retrieveAttachmentImages('123');
        await comp.$nextTick();

        // THEN
        expect(comp.attachmentImages).toBe(foundAttachmentImages);
      });
    });
  });
});
