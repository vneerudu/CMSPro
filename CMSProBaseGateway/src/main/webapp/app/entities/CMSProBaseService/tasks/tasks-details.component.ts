import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';
import TasksService from './tasks.service';

@Component
export default class TasksDetails extends Vue {
  @Inject('tasksService') private tasksService: () => TasksService;
  public tasks: ITasks = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tasksId) {
        vm.retrieveTasks(to.params.tasksId);
      }
    });
  }

  public retrieveTasks(tasksId) {
    this.tasksService()
      .find(tasksId)
      .then(res => {
        this.tasks = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
