import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskStatuses, TaskStatuses } from '@/shared/model/CMSProBaseService/task-statuses.model';
import TaskStatusesService from './task-statuses.service';

const validations: any = {
  taskStatuses: {
    code: {
      required,
    },
    description: {
      required,
    },
    isActive: {},
  },
};

@Component({
  validations,
})
export default class TaskStatusesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskStatusesService') private taskStatusesService: () => TaskStatusesService;
  public taskStatuses: ITaskStatuses = new TaskStatuses();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStatusesId) {
        vm.retrieveTaskStatuses(to.params.taskStatusesId);
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
    if (this.taskStatuses.id) {
      this.taskStatusesService()
        .update(this.taskStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskStatusesService()
        .create(this.taskStatuses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskStatuses(taskStatusesId): void {
    this.taskStatusesService()
      .find(taskStatusesId)
      .then(res => {
        this.taskStatuses = res;
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
