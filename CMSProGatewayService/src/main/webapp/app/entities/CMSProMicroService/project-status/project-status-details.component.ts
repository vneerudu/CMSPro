import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProjectStatus } from '@/shared/model/CMSProMicroService/project-status.model';
import ProjectStatusService from './project-status.service';

@Component
export default class ProjectStatusDetails extends Vue {
  @Inject('projectStatusService') private projectStatusService: () => ProjectStatusService;
  public projectStatus: IProjectStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectStatusId) {
        vm.retrieveProjectStatus(to.params.projectStatusId);
      }
    });
  }

  public retrieveProjectStatus(projectStatusId) {
    this.projectStatusService()
      .find(projectStatusId)
      .then(res => {
        this.projectStatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
