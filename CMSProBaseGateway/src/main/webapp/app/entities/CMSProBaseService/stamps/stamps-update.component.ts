import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { IStamps, Stamps } from '@/shared/model/CMSProBaseService/stamps.model';
import StampsService from './stamps.service';

const validations: any = {
  stamps: {
    stamp: {
      required,
    },
    title: {
      required,
    },
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
export default class StampsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stampsService') private stampsService: () => StampsService;
  public stamps: IStamps = new Stamps();

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stampsId) {
        vm.retrieveStamps(to.params.stampsId);
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
    if (this.stamps.id) {
      this.stampsService()
        .update(this.stamps)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stampsService()
        .create(this.stamps)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStamps(stampsId): void {
    this.stampsService()
      .find(stampsId)
      .then(res => {
        this.stamps = res;
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
