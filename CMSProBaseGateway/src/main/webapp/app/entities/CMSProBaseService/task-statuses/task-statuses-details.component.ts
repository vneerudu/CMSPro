import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskStatuses } from '@/shared/model/CMSProBaseService/task-statuses.model';
import TaskStatusesService from './task-statuses.service';

@Component
export default class TaskStatusesDetails extends Vue {
  @Inject('taskStatusesService') private taskStatusesService: () => TaskStatusesService;
  public taskStatuses: ITaskStatuses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStatusesId) {
        vm.retrieveTaskStatuses(to.params.taskStatusesId);
      }
    });
  }

  public retrieveTaskStatuses(taskStatusesId) {
    this.taskStatusesService()
      .find(taskStatusesId)
      .then(res => {
        this.taskStatuses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
