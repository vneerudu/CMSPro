import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AddressesService from '../addresses/addresses.service';
import { IAddresses } from '@/shared/model/CMSProBaseService/addresses.model';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import ProjectTeamsService from '../project-teams/project-teams.service';
import { IProjectTeams } from '@/shared/model/CMSProBaseService/project-teams.model';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import StampsService from '../stamps/stamps.service';
import { IStamps } from '@/shared/model/CMSProBaseService/stamps.model';

import ListsService from '../lists/lists.service';
import { ILists } from '@/shared/model/CMSProBaseService/lists.model';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AccountsService from '../accounts/accounts.service';
import { IAccounts } from '@/shared/model/CMSProBaseService/accounts.model';

import AlertService from '@/shared/alert/alert.service';
import { IProjects, Projects } from '@/shared/model/CMSProBaseService/projects.model';
import ProjectsService from './projects.service';

const validations: any = {
  projects: {
    code: {},
    name: {
      required,
    },
    startDate: {},
    finishDate: {},
    lastUpdate: {},
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
export default class ProjectsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('projectsService') private projectsService: () => ProjectsService;
  public projects: IProjects = new Projects();

  @Inject('addressesService') private addressesService: () => AddressesService;

  public addresses: IAddresses[] = [];

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];

  @Inject('projectTeamsService') private projectTeamsService: () => ProjectTeamsService;

  public projectTeams: IProjectTeams[] = [];

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('stampsService') private stampsService: () => StampsService;

  public stamps: IStamps[] = [];

  @Inject('listsService') private listsService: () => ListsService;

  public lists: ILists[] = [];

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];

  @Inject('accountsService') private accountsService: () => AccountsService;

  public accounts: IAccounts[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.projectsId) {
        vm.retrieveProjects(to.params.projectsId);
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
    if (this.projects.id) {
      this.projectsService()
        .update(this.projects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.projectsService()
        .create(this.projects)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProjects(projectsId): void {
    this.projectsService()
      .find(projectsId)
      .then(res => {
        this.projects = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.addressesService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
    this.sheetsService()
      .retrieve()
      .then(res => {
        this.sheets = res.data;
      });
    this.projectTeamsService()
      .retrieve()
      .then(res => {
        this.projectTeams = res.data;
      });
    this.tasksService()
      .retrieve()
      .then(res => {
        this.tasks = res.data;
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
    this.rFIService()
      .retrieve()
      .then(res => {
        this.rFIS = res.data;
      });
    this.accountsService()
      .retrieve()
      .then(res => {
        this.accounts = res.data;
      });
  }
}
