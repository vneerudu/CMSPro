import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskLocations } from '@/shared/model/CMSProBaseService/task-locations.model';
import TaskLocationsService from './task-locations.service';

@Component
export default class TaskLocationsDetails extends Vue {
  @Inject('taskLocationsService') private taskLocationsService: () => TaskLocationsService;
  public taskLocations: ITaskLocations = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskLocationsId) {
        vm.retrieveTaskLocations(to.params.taskLocationsId);
      }
    });
  }

  public retrieveTaskLocations(taskLocationsId) {
    this.taskLocationsService()
      .find(taskLocationsId)
      .then(res => {
        this.taskLocations = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
