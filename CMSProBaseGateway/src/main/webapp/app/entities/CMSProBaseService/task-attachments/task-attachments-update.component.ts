import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ITaskAttachments, TaskAttachments } from '@/shared/model/CMSProBaseService/task-attachments.model';
import TaskAttachmentsService from './task-attachments.service';

const validations: any = {
  taskAttachments: {
    folder: {},
    fileName: {
      required,
    },
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
export default class TaskAttachmentsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskAttachmentsService') private taskAttachmentsService: () => TaskAttachmentsService;
  public taskAttachments: ITaskAttachments = new TaskAttachments();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskAttachmentsId) {
        vm.retrieveTaskAttachments(to.params.taskAttachmentsId);
      }
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
    if (this.taskAttachments.id) {
      this.taskAttachmentsService()
        .update(this.taskAttachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskAttachmentsService()
        .create(this.taskAttachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskAttachments(taskAttachmentsId): void {
    this.taskAttachmentsService()
      .find(taskAttachmentsId)
      .then(res => {
        this.taskAttachments = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
