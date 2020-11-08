import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import RootCauseGroupsService from '../root-cause-groups/root-cause-groups.service';
import { IRootCauseGroups } from '@/shared/model/CMSProBaseService/root-cause-groups.model';

import AlertService from '@/shared/alert/alert.service';
import { IRootCauses, RootCauses } from '@/shared/model/CMSProBaseService/root-causes.model';
import RootCausesService from './root-causes.service';

const validations: any = {
  rootCauses: {
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
export default class RootCausesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rootCausesService') private rootCausesService: () => RootCausesService;
  public rootCauses: IRootCauses = new RootCauses();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('rootCauseGroupsService') private rootCauseGroupsService: () => RootCauseGroupsService;

  public rootCauseGroups: IRootCauseGroups[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rootCausesId) {
        vm.retrieveRootCauses(to.params.rootCausesId);
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
    if (this.rootCauses.id) {
      this.rootCausesService()
        .update(this.rootCauses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rootCausesService()
        .create(this.rootCauses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRootCauses(rootCausesId): void {
    this.rootCausesService()
      .find(rootCausesId)
      .then(res => {
        this.rootCauses = res;
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
    this.rootCauseGroupsService()
      .retrieve()
      .then(res => {
        this.rootCauseGroups = res.data;
      });
  }
}
