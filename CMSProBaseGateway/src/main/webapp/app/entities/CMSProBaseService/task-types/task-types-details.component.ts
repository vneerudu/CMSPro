import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskTypes } from '@/shared/model/CMSProBaseService/task-types.model';
import TaskTypesService from './task-types.service';

@Component
export default class TaskTypesDetails extends Vue {
  @Inject('taskTypesService') private taskTypesService: () => TaskTypesService;
  public taskTypes: ITaskTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskTypesId) {
        vm.retrieveTaskTypes(to.params.taskTypesId);
      }
    });
  }

  public retrieveTaskTypes(taskTypesId) {
    this.taskTypesService()
      .find(taskTypesId)
      .then(res => {
        this.taskTypes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
