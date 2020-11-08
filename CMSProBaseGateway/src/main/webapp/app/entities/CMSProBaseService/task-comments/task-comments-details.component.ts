import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskComments } from '@/shared/model/CMSProBaseService/task-comments.model';
import TaskCommentsService from './task-comments.service';

@Component
export default class TaskCommentsDetails extends Vue {
  @Inject('taskCommentsService') private taskCommentsService: () => TaskCommentsService;
  public taskComments: ITaskComments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskCommentsId) {
        vm.retrieveTaskComments(to.params.taskCommentsId);
      }
    });
  }

  public retrieveTaskComments(taskCommentsId) {
    this.taskCommentsService()
      .find(taskCommentsId)
      .then(res => {
        this.taskComments = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
