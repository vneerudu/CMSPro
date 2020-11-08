import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ITaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';
import TaskAttachmentOthersService from './task-attachment-others.service';

@Component
export default class TaskAttachmentOthersDetails extends mixins(JhiDataUtils) {
  @Inject('taskAttachmentOthersService') private taskAttachmentOthersService: () => TaskAttachmentOthersService;
  public taskAttachmentOthers: ITaskAttachmentOthers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentOthersId) {
        vm.retrieveTaskAttachmentOthers(to.params.taskAttachmentOthersId);
      }
    });
  }

  public retrieveTaskAttachmentOthers(taskAttachmentOthersId) {
    this.taskAttachmentOthersService()
      .find(taskAttachmentOthersId)
      .then(res => {
        this.taskAttachmentOthers = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
