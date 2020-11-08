import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskComments, TaskComments } from '@/shared/model/CMSProBaseService/task-comments.model';
import TaskCommentsService from './task-comments.service';

const validations: any = {
  taskComments: {
    createdBy: {
      required,
    },
    comment: {
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
export default class TaskCommentsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskCommentsService') private taskCommentsService: () => TaskCommentsService;
  public taskComments: ITaskComments = new TaskComments();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskCommentsId) {
        vm.retrieveTaskComments(to.params.taskCommentsId);
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
    if (this.taskComments.id) {
      this.taskCommentsService()
        .update(this.taskComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskCommentsService()
        .create(this.taskComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskComments(taskCommentsId): void {
    this.taskCommentsService()
      .find(taskCommentsId)
      .then(res => {
        this.taskComments = res;
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
  }
}
