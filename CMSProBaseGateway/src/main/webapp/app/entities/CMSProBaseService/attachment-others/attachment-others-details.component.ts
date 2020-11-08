import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IAttachmentOthers } from '@/shared/model/CMSProBaseService/attachment-others.model';
import AttachmentOthersService from './attachment-others.service';

@Component
export default class AttachmentOthersDetails extends mixins(JhiDataUtils) {
  @Inject('attachmentOthersService') private attachmentOthersService: () => AttachmentOthersService;
  public attachmentOthers: IAttachmentOthers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentOthersId) {
        vm.retrieveAttachmentOthers(to.params.attachmentOthersId);
      }
    });
  }

  public retrieveAttachmentOthers(attachmentOthersId) {
    this.attachmentOthersService()
      .find(attachmentOthersId)
      .then(res => {
        this.attachmentOthers = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
