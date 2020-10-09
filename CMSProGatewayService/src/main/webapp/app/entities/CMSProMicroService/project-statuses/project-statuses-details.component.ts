import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProjectStatuses } from '@/shared/model/CMSProMicroService/project-statuses.model';
import ProjectStatusesService from './project-statuses.service';

@Component
export default class ProjectStatusesDetails extends Vue {
  @Inject('projectStatusesService') private projectStatusesService: () => ProjectStatusesService;
  public projectStatuses: IProjectStatuses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectStatusesId) {
        vm.retrieveProjectStatuses(to.params.projectStatusesId);
      }
    });
  }

  public retrieveProjectStatuses(projectStatusesId) {
    this.projectStatusesService()
      .find(projectStatusesId)
      .then(res => {
        this.projectStatuses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
