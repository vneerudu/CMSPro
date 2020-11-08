import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import UserGroupsService from '../user-groups/user-groups.service';
import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';

import AddressesService from '../addresses/addresses.service';
import { IAddresses } from '@/shared/model/CMSProBaseService/addresses.model';

import AccountsService from '../accounts/accounts.service';
import { IAccounts } from '@/shared/model/CMSProBaseService/accounts.model';

import ProjectTeamsService from '../project-teams/project-teams.service';
import { IProjectTeams } from '@/shared/model/CMSProBaseService/project-teams.model';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import AlertService from '@/shared/alert/alert.service';
import { IUsers, Users } from '@/shared/model/CMSProBaseService/users.model';
import UsersService from './users.service';

const validations: any = {
  users: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    fullName: {},
    prefix: {},
    emailAddress: {},
    phoneNumber: {},
    title: {},
    company: {},
    trackLocation: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class UsersUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('usersService') private usersService: () => UsersService;
  public users: IUsers = new Users();

  @Inject('userGroupsService') private userGroupsService: () => UserGroupsService;

  public userGroups: IUserGroups[] = [];

  @Inject('addressesService') private addressesService: () => AddressesService;

  public addresses: IAddresses[] = [];

  @Inject('accountsService') private accountsService: () => AccountsService;

  public accounts: IAccounts[] = [];

  @Inject('projectTeamsService') private projectTeamsService: () => ProjectTeamsService;

  public projectTeams: IProjectTeams[] = [];

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usersId) {
        vm.retrieveUsers(to.params.usersId);
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
    if (this.users.id) {
      this.usersService()
        .update(this.users)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.usersService()
        .create(this.users)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUsers(usersId): void {
    this.usersService()
      .find(usersId)
      .then(res => {
        this.users = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userGroupsService()
      .retrieve()
      .then(res => {
        this.userGroups = res.data;
      });
    this.addressesService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
    this.accountsService()
      .retrieve()
      .then(res => {
        this.accounts = res.data;
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
    this.tasksService()
      .retrieve()
      .then(res => {
        this.tasks = res.data;
      });
  }
}
