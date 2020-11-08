import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAttachments } from '@/shared/model/CMSProBaseService/attachments.model';
import AttachmentsService from './attachments.service';

@Component
export default class AttachmentsDetails extends Vue {
  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;
  public attachments: IAttachments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentsId) {
        vm.retrieveAttachments(to.params.attachmentsId);
      }
    });
  }

  public retrieveAttachments(attachmentsId) {
    this.attachmentsService()
      .find(attachmentsId)
      .then(res => {
        this.attachments = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
