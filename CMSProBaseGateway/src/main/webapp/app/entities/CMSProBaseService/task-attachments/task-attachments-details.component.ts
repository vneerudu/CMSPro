import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskAttachments } from '@/shared/model/CMSProBaseService/task-attachments.model';
import TaskAttachmentsService from './task-attachments.service';

@Component
export default class TaskAttachmentsDetails extends Vue {
  @Inject('taskAttachmentsService') private taskAttachmentsService: () => TaskAttachmentsService;
  public taskAttachments: ITaskAttachments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentsId) {
        vm.retrieveTaskAttachments(to.params.taskAttachmentsId);
      }
    });
  }

  public retrieveTaskAttachments(taskAttachmentsId) {
    this.taskAttachmentsService()
      .find(taskAttachmentsId)
      .then(res => {
        this.taskAttachments = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
