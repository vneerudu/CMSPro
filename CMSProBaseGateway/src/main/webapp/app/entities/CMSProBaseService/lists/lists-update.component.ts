import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { ILists, Lists } from '@/shared/model/CMSProBaseService/lists.model';
import ListsService from './lists.service';

const validations: any = {
  lists: {
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
export default class ListsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('listsService') private listsService: () => ListsService;
  public lists: ILists = new Lists();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listsId) {
        vm.retrieveLists(to.params.listsId);
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
    if (this.lists.id) {
      this.listsService()
        .update(this.lists)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.listsService()
        .create(this.lists)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLists(listsId): void {
    this.listsService()
      .find(listsId)
      .then(res => {
        this.lists = res;
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
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
