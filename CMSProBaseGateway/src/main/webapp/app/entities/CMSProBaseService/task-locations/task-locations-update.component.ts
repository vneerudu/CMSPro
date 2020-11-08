import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import AlertService from '@/shared/alert/alert.service';
import { ITaskLocations, TaskLocations } from '@/shared/model/CMSProBaseService/task-locations.model';
import TaskLocationsService from './task-locations.service';

const validations: any = {
  taskLocations: {
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
export default class TaskLocationsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('taskLocationsService') private taskLocationsService: () => TaskLocationsService;
  public taskLocations: ITaskLocations = new TaskLocations();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskLocationsId) {
        vm.retrieveTaskLocations(to.params.taskLocationsId);
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
    if (this.taskLocations.id) {
      this.taskLocationsService()
        .update(this.taskLocations)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.taskLocationsService()
        .create(this.taskLocations)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTaskLocations(taskLocationsId): void {
    this.taskLocationsService()
      .find(taskLocationsId)
      .then(res => {
        this.taskLocations = res;
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
