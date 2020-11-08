import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import LanguagesService from '../languages/languages.service';
import { ILanguages } from '@/shared/model/CMSProBaseService/languages.model';

import LogosService from '../logos/logos.service';
import { ILogos } from '@/shared/model/CMSProBaseService/logos.model';

import AccountStatusesService from '../account-statuses/account-statuses.service';
import { IAccountStatuses } from '@/shared/model/CMSProBaseService/account-statuses.model';

import UsersService from '../users/users.service';
import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

import UserGroupsService from '../user-groups/user-groups.service';
import { IUserGroups } from '@/shared/model/CMSProBaseService/user-groups.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { IAccounts, Accounts } from '@/shared/model/CMSProBaseService/accounts.model';
import AccountsService from './accounts.service';

const validations: any = {
  accounts: {
    accountNumber: {
      required,
      numeric,
    },
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    emailAddress: {
      required,
    },
    phoneNumber: {
      required,
    },
    creationDate: {
      required,
    },
    createdBy: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class AccountsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('accountsService') private accountsService: () => AccountsService;
  public accounts: IAccounts = new Accounts();

  @Inject('languagesService') private languagesService: () => LanguagesService;

  public languages: ILanguages[] = [];

  @Inject('logosService') private logosService: () => LogosService;

  public logos: ILogos[] = [];

  @Inject('accountStatusesService') private accountStatusesService: () => AccountStatusesService;

  public accountStatuses: IAccountStatuses[] = [];

  @Inject('usersService') private usersService: () => UsersService;

  public users: IUsers[] = [];

  @Inject('userGroupsService') private userGroupsService: () => UserGroupsService;

  public userGroups: IUserGroups[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.accountsId) {
        vm.retrieveAccounts(to.params.accountsId);
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
    if (this.accounts.id) {
      this.accountsService()
        .update(this.accounts)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.accountsService()
        .create(this.accounts)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAccounts(accountsId): void {
    this.accountsService()
      .find(accountsId)
      .then(res => {
        this.accounts = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.languagesService()
      .retrieve()
      .then(res => {
        this.languages = res.data;
      });
    this.logosService()
      .retrieve()
      .then(res => {
        this.logos = res.data;
      });
    this.accountStatusesService()
      .retrieve()
      .then(res => {
        this.accountStatuses = res.data;
      });
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.userGroupsService()
      .retrieve()
      .then(res => {
        this.userGroups = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
