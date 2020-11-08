import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskAttachmentImages, TaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';
import TaskAttachmentImagesService from './task-attachment-images.service';

const validations: any = {
  taskAttachmentImages: {
    fileName: {
      required,
    },
    fileType: {},
    content: {},
    createdBy: {
      required,
    },
    creationDate: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TaskAttachmentImagesUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskAttachmentImagesService') private taskAttachmentImagesService: () => TaskAttachmentImagesService;
  public taskAttachmentImages: ITaskAttachmentImages = new TaskAttachmentImages();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentImagesId) {
        vm.retrieveTaskAttachmentImages(to.params.taskAttachmentImagesId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.taskAttachmentImages.id) {
      this.taskAttachmentImagesService()
        .update(this.taskAttachmentImages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskAttachmentImagesService()
        .create(this.taskAttachmentImages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskAttachmentImages(taskAttachmentImagesId): void {
    this.taskAttachmentImagesService()
      .find(taskAttachmentImagesId)
      .then(res => {
        this.taskAttachmentImages = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tasksService()
      .retrieve()
      .then(res => {
        this.tasks = res.data;
      });
    this.rFIService()
      .retrieve()
      .then(res => {
        this.rFIS = res.data;
      });
  }
}
