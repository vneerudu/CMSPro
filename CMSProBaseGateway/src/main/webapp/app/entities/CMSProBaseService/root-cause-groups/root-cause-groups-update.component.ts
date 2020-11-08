import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import RootCausesService from '../root-causes/root-causes.service';
import { IRootCauses } from '@/shared/model/CMSProBaseService/root-causes.model';

import AlertService from '@/shared/alert/alert.service';
import { IRootCauseGroups, RootCauseGroups } from '@/shared/model/CMSProBaseService/root-cause-groups.model';
import RootCauseGroupsService from './root-cause-groups.service';

const validations: any = {
  rootCauseGroups: {
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
export default class RootCauseGroupsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rootCauseGroupsService') private rootCauseGroupsService: () => RootCauseGroupsService;
  public rootCauseGroups: IRootCauseGroups = new RootCauseGroups();

  @Inject('rootCausesService') private rootCausesService: () => RootCausesService;

  public rootCauses: IRootCauses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rootCauseGroupsId) {
        vm.retrieveRootCauseGroups(to.params.rootCauseGroupsId);
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
    if (this.rootCauseGroups.id) {
      this.rootCauseGroupsService()
        .update(this.rootCauseGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rootCauseGroupsService()
        .create(this.rootCauseGroups)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRootCauseGroups(rootCauseGroupsId): void {
    this.rootCauseGroupsService()
      .find(rootCauseGroupsId)
      .then(res => {
        this.rootCauseGroups = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.rootCausesService()
      .retrieve()
      .then(res => {
        this.rootCauses = res.data;
      });
  }
}
