import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProjects } from '@/shared/model/CMSProMicroService/projects.model';
import ProjectsService from './projects.service';

@Component
export default class ProjectsDetails extends Vue {
  @Inject('projectsService') private projectsService: () => ProjectsService;
  public projects: IProjects = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectsId) {
        vm.retrieveProjects(to.params.projectsId);
      }
    });
  }

  public retrieveProjects(projectsId) {
    this.projectsService()
      .find(projectsId)
      .then(res => {
        this.projects = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
