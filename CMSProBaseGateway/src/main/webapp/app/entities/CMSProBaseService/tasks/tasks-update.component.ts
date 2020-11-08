import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TaskTypesService from '../task-types/task-types.service';
import { ITaskTypes } from '@/shared/model/CMSProBaseService/task-types.model';

import TaskStatusesService from '../task-statuses/task-statuses.service';
import { ITaskStatuses } from '@/shared/model/CMSProBaseService/task-statuses.model';

import TaskLocationsService from '../task-locations/task-locations.service';
import { ITaskLocations } from '@/shared/model/CMSProBaseService/task-locations.model';

import StampsService from '../stamps/stamps.service';
import { IStamps } from '@/shared/model/CMSProBaseService/stamps.model';

import ListsService from '../lists/lists.service';
import { ILists } from '@/shared/model/CMSProBaseService/lists.model';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import RootCausesService from '../root-causes/root-causes.service';
import { IRootCauses } from '@/shared/model/CMSProBaseService/root-causes.model';

import UsersService from '../users/users.service';
import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

import TaskAttachmentImagesService from '../task-attachment-images/task-attachment-images.service';
import { ITaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';

import TaskAttachmentOthersService from '../task-attachment-others/task-attachment-others.service';
import { ITaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';

import TaskCommentsService from '../task-comments/task-comments.service';
import { ITaskComments } from '@/shared/model/CMSProBaseService/task-comments.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { ITasks, Tasks } from '@/shared/model/CMSProBaseService/tasks.model';
import TasksService from './tasks.service';

const validations: any = {
  tasks: {
    title: {
      required,
    },
    startDate: {},
    dueDate: {},
    description: {},
    costImpact: {},
    costImpactComment: {},
    scheduleImpact: {},
    scheduleImpactComment: {},
    createdBy: {
      required,
    },
    creationDate: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TasksUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('tasksService') private tasksService: () => TasksService;
  public tasks: ITasks = new Tasks();

  @Inject('taskTypesService') private taskTypesService: () => TaskTypesService;

  public taskTypes: ITaskTypes[] = [];

  @Inject('taskStatusesService') private taskStatusesService: () => TaskStatusesService;

  public taskStatuses: ITaskStatuses[] = [];

  @Inject('taskLocationsService') private taskLocationsService: () => TaskLocationsService;

  public taskLocations: ITaskLocations[] = [];

  @Inject('stampsService') private stampsService: () => StampsService;

  public stamps: IStamps[] = [];

  @Inject('listsService') private listsService: () => ListsService;

  public lists: ILists[] = [];

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];

  @Inject('rootCausesService') private rootCausesService: () => RootCausesService;

  public rootCauses: IRootCauses[] = [];

  @Inject('usersService') private usersService: () => UsersService;

  public users: IUsers[] = [];

  @Inject('taskAttachmentImagesService') private taskAttachmentImagesService: () => TaskAttachmentImagesService;

  public taskAttachmentImages: ITaskAttachmentImages[] = [];

  @Inject('taskAttachmentOthersService') private taskAttachmentOthersService: () => TaskAttachmentOthersService;

  public taskAttachmentOthers: ITaskAttachmentOthers[] = [];

  @Inject('taskCommentsService') private taskCommentsService: () => TaskCommentsService;

  public taskComments: ITaskComments[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tasksId) {
        vm.retrieveTasks(to.params.tasksId);
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
    if (this.tasks.id) {
      this.tasksService()
        .update(this.tasks)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.tasksService()
        .create(this.tasks)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTasks(tasksId): void {
    this.tasksService()
      .find(tasksId)
      .then(res => {
        this.tasks = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.taskTypesService()
      .retrieve()
      .then(res => {
        this.taskTypes = res.data;
      });
    this.taskStatusesService()
      .retrieve()
      .then(res => {
        this.taskStatuses = res.data;
      });
    this.taskLocationsService()
      .retrieve()
      .then(res => {
        this.taskLocations = res.data;
      });
    this.stampsService()
      .retrieve()
      .then(res => {
        this.stamps = res.data;
      });
    this.listsService()
      .retrieve()
      .then(res => {
        this.lists = res.data;
      });
    this.sheetsService()
      .retrieve()
      .then(res => {
        this.sheets = res.data;
      });
    this.rootCausesService()
      .retrieve()
      .then(res => {
        this.rootCauses = res.data;
      });
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.taskAttachmentImagesService()
      .retrieve()
      .then(res => {
        this.taskAttachmentImages = res.data;
      });
    this.taskAttachmentOthersService()
      .retrieve()
      .then(res => {
        this.taskAttachmentOthers = res.data;
      });
    this.taskCommentsService()
      .retrieve()
      .then(res => {
        this.taskComments = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
