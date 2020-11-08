import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IAttachmentImages } from '@/shared/model/CMSProBaseService/attachment-images.model';
import AttachmentImagesService from './attachment-images.service';

@Component
export default class AttachmentImagesDetails extends mixins(JhiDataUtils) {
  @Inject('attachmentImagesService') private attachmentImagesService: () => AttachmentImagesService;
  public attachmentImages: IAttachmentImages = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentImagesId) {
        vm.retrieveAttachmentImages(to.params.attachmentImagesId);
      }
    });
  }

  public retrieveAttachmentImages(attachmentImagesId) {
    this.attachmentImagesService()
      .find(attachmentImagesId)
      .then(res => {
        this.attachmentImages = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
