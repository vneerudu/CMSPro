import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProjectTeams } from '@/shared/model/CMSProBaseService/project-teams.model';
import ProjectTeamsService from './project-teams.service';

@Component
export default class ProjectTeamsDetails extends Vue {
  @Inject('projectTeamsService') private projectTeamsService: () => ProjectTeamsService;
  public projectTeams: IProjectTeams = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectTeamsId) {
        vm.retrieveProjectTeams(to.params.projectTeamsId);
      }
    });
  }

  public retrieveProjectTeams(projectTeamsId) {
    this.projectTeamsService()
      .find(projectTeamsId)
      .then(res => {
        this.projectTeams = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
