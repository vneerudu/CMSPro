import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskAttachmentOthers, TaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';
import TaskAttachmentOthersService from './task-attachment-others.service';

const validations: any = {
  taskAttachmentOthers: {
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
export default class TaskAttachmentOthersUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskAttachmentOthersService') private taskAttachmentOthersService: () => TaskAttachmentOthersService;
  public taskAttachmentOthers: ITaskAttachmentOthers = new TaskAttachmentOthers();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentOthersId) {
        vm.retrieveTaskAttachmentOthers(to.params.taskAttachmentOthersId);
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
    if (this.taskAttachmentOthers.id) {
      this.taskAttachmentOthersService()
        .update(this.taskAttachmentOthers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskAttachmentOthersService()
        .create(this.taskAttachmentOthers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskAttachmentOthers(taskAttachmentOthersId): void {
    this.taskAttachmentOthersService()
      .find(taskAttachmentOthersId)
      .then(res => {
        this.taskAttachmentOthers = res;
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
