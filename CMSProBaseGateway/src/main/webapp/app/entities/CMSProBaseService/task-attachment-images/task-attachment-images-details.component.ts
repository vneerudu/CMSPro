import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ITaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';
import TaskAttachmentImagesService from './task-attachment-images.service';

@Component
export default class TaskAttachmentImagesDetails extends mixins(JhiDataUtils) {
  @Inject('taskAttachmentImagesService') private taskAttachmentImagesService: () => TaskAttachmentImagesService;
  public taskAttachmentImages: ITaskAttachmentImages = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentImagesId) {
        vm.retrieveTaskAttachmentImages(to.params.taskAttachmentImagesId);
      }
    });
  }

  public retrieveTaskAttachmentImages(taskAttachmentImagesId) {
    this.taskAttachmentImagesService()
      .find(taskAttachmentImagesId)
      .then(res => {
        this.taskAttachmentImages = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
