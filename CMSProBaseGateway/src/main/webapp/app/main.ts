// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import GatewayService from '@/admin/gateway/gateway.service';

import TrackerService from './admin/tracker/tracker.service';
/* tslint:disable */

import LanguagesService from '@/entities/CMSProBaseService/languages/languages.service';
import LogosService from '@/entities/CMSProBaseService/logos/logos.service';
import AccountStatusesService from '@/entities/CMSProBaseService/account-statuses/account-statuses.service';
import AccountsService from '@/entities/CMSProBaseService/accounts/accounts.service';
import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';
import UsersService from '@/entities/CMSProBaseService/users/users.service';
import AddressTypesService from '@/entities/CMSProBaseService/address-types/address-types.service';
import AddressesService from '@/entities/CMSProBaseService/addresses/addresses.service';
import StatesService from '@/entities/CMSProBaseService/states/states.service';
import CountryService from '@/entities/CMSProBaseService/country/country.service';
import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';
import UserPermissionsService from '@/entities/CMSProBaseService/user-permissions/user-permissions.service';
import MenuItemsService from '@/entities/CMSProBaseService/menu-items/menu-items.service';
import ProjectsService from '@/entities/CMSProBaseService/projects/projects.service';
import SheetTagsService from '@/entities/CMSProBaseService/sheet-tags/sheet-tags.service';
import SheetsService from '@/entities/CMSProBaseService/sheets/sheets.service';
import SheetHistoryService from '@/entities/CMSProBaseService/sheet-history/sheet-history.service';
import SheetCommentsService from '@/entities/CMSProBaseService/sheet-comments/sheet-comments.service';
import DocumentsService from '@/entities/CMSProBaseService/documents/documents.service';
import AttachmentsService from '@/entities/CMSProBaseService/attachments/attachments.service';
import AttachmentImagesService from '@/entities/CMSProBaseService/attachment-images/attachment-images.service';
import AttachmentOthersService from '@/entities/CMSProBaseService/attachment-others/attachment-others.service';
import ProjectTeamsService from '@/entities/CMSProBaseService/project-teams/project-teams.service';
import TaskStatusesService from '@/entities/CMSProBaseService/task-statuses/task-statuses.service';
import TaskTypesService from '@/entities/CMSProBaseService/task-types/task-types.service';
import ListsService from '@/entities/CMSProBaseService/lists/lists.service';
import StampsService from '@/entities/CMSProBaseService/stamps/stamps.service';
import RootCauseGroupsService from '@/entities/CMSProBaseService/root-cause-groups/root-cause-groups.service';
import RootCausesService from '@/entities/CMSProBaseService/root-causes/root-causes.service';
import TaskAttachmentsService from '@/entities/CMSProBaseService/task-attachments/task-attachments.service';
import TaskAttachmentImagesService from '@/entities/CMSProBaseService/task-attachment-images/task-attachment-images.service';
import TaskAttachmentOthersService from '@/entities/CMSProBaseService/task-attachment-others/task-attachment-others.service';
import TaskCommentsService from '@/entities/CMSProBaseService/task-comments/task-comments.service';
import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';
import TaskLocationsService from '@/entities/CMSProBaseService/task-locations/task-locations.service';
import RFIStatusesService from '@/entities/CMSProBaseService/rfi-statuses/rfi-statuses.service';
import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';
import RFICommentsService from '@/entities/CMSProBaseService/rfi-comments/rfi-comments.service';
import RFITimeLineService from '@/entities/CMSProBaseService/rfi-time-line/rfi-time-line.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const trackerService = new TrackerService(router);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, trackerService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    gatewayService: () => new GatewayService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    trackerService: () => trackerService,
    alertService: () => alertService,
    translationService: () => translationService,
    languagesService: () => new LanguagesService(),
    logosService: () => new LogosService(),
    accountStatusesService: () => new AccountStatusesService(),
    accountsService: () => new AccountsService(),
    userGroupsService: () => new UserGroupsService(),
    usersService: () => new UsersService(),
    addressTypesService: () => new AddressTypesService(),
    addressesService: () => new AddressesService(),
    statesService: () => new StatesService(),
    countryService: () => new CountryService(),
    userRolesService: () => new UserRolesService(),
    userPermissionsService: () => new UserPermissionsService(),
    menuItemsService: () => new MenuItemsService(),
    projectsService: () => new ProjectsService(),
    sheetTagsService: () => new SheetTagsService(),
    sheetsService: () => new SheetsService(),
    sheetHistoryService: () => new SheetHistoryService(),
    sheetCommentsService: () => new SheetCommentsService(),
    documentsService: () => new DocumentsService(),
    attachmentsService: () => new AttachmentsService(),
    attachmentImagesService: () => new AttachmentImagesService(),
    attachmentOthersService: () => new AttachmentOthersService(),
    projectTeamsService: () => new ProjectTeamsService(),
    taskStatusesService: () => new TaskStatusesService(),
    taskTypesService: () => new TaskTypesService(),
    listsService: () => new ListsService(),
    stampsService: () => new StampsService(),
    rootCauseGroupsService: () => new RootCauseGroupsService(),
    rootCausesService: () => new RootCausesService(),
    taskAttachmentsService: () => new TaskAttachmentsService(),
    taskAttachmentImagesService: () => new TaskAttachmentImagesService(),
    taskAttachmentOthersService: () => new TaskAttachmentOthersService(),
    taskCommentsService: () => new TaskCommentsService(),
    tasksService: () => new TasksService(),
    taskLocationsService: () => new TaskLocationsService(),
    rFIStatusesService: () => new RFIStatusesService(),
    rFIService: () => new RFIService(),
    rFICommentsService: () => new RFICommentsService(),
    rFITimeLineService: () => new RFITimeLineService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
  },
  i18n,
  store,
});
