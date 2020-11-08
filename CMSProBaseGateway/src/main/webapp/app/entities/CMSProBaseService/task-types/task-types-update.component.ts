import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskTypes, TaskTypes } from '@/shared/model/CMSProBaseService/task-types.model';
import TaskTypesService from './task-types.service';

const validations: any = {
  taskTypes: {
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
export default class TaskTypesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskTypesService') private taskTypesService: () => TaskTypesService;
  public taskTypes: ITaskTypes = new TaskTypes();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskTypesId) {
        vm.retrieveTaskTypes(to.params.taskTypesId);
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
    if (this.taskTypes.id) {
      this.taskTypesService()
        .update(this.taskTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskTypesService()
        .create(this.taskTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskTypes(taskTypesId): void {
    this.taskTypesService()
      .find(taskTypesId)
      .then(res => {
        this.taskTypes = res;
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
