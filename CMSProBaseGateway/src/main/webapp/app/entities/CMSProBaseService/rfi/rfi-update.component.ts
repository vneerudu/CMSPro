import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import RFIStatusesService from '../rfi-statuses/rfi-statuses.service';
import { IRFIStatuses } from '@/shared/model/CMSProBaseService/rfi-statuses.model';

import TaskAttachmentImagesService from '../task-attachment-images/task-attachment-images.service';
import { ITaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';

import TaskAttachmentOthersService from '../task-attachment-others/task-attachment-others.service';
import { ITaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';

import AttachmentOthersService from '../attachment-others/attachment-others.service';
import { IAttachmentOthers } from '@/shared/model/CMSProBaseService/attachment-others.model';

import AttachmentImagesService from '../attachment-images/attachment-images.service';
import { IAttachmentImages } from '@/shared/model/CMSProBaseService/attachment-images.model';

import RFICommentsService from '../rfi-comments/rfi-comments.service';
import { IRFIComments } from '@/shared/model/CMSProBaseService/rfi-comments.model';

import RFITimeLineService from '../rfi-time-line/rfi-time-line.service';
import { IRFITimeLine } from '@/shared/model/CMSProBaseService/rfi-time-line.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { IRFI, RFI } from '@/shared/model/CMSProBaseService/rfi.model';
import RFIService from './rfi.service';

const validations: any = {
  rFI: {
    title: {
      required,
    },
    question: {
      required,
    },
    answer: {},
    sentDate: {},
    dueDate: {},
    locked: {},
    lockedBy: {},
  },
};

@Component({
  validations,
})
export default class RFIUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rFIService') private rFIService: () => RFIService;
  public rFI: IRFI = new RFI();

  @Inject('rFIStatusesService') private rFIStatusesService: () => RFIStatusesService;

  public rFIStatuses: IRFIStatuses[] = [];

  @Inject('taskAttachmentImagesService') private taskAttachmentImagesService: () => TaskAttachmentImagesService;

  public taskAttachmentImages: ITaskAttachmentImages[] = [];

  @Inject('taskAttachmentOthersService') private taskAttachmentOthersService: () => TaskAttachmentOthersService;

  public taskAttachmentOthers: ITaskAttachmentOthers[] = [];

  @Inject('attachmentOthersService') private attachmentOthersService: () => AttachmentOthersService;

  public attachmentOthers: IAttachmentOthers[] = [];

  @Inject('attachmentImagesService') private attachmentImagesService: () => AttachmentImagesService;

  public attachmentImages: IAttachmentImages[] = [];

  @Inject('rFICommentsService') private rFICommentsService: () => RFICommentsService;

  public rFIComments: IRFIComments[] = [];

  @Inject('rFITimeLineService') private rFITimeLineService: () => RFITimeLineService;

  public rFITimeLines: IRFITimeLine[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rFIId) {
        vm.retrieveRFI(to.params.rFIId);
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
    if (this.rFI.id) {
      this.rFIService()
        .update(this.rFI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rFIService()
        .create(this.rFI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRFI(rFIId): void {
    this.rFIService()
      .find(rFIId)
      .then(res => {
        this.rFI = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.rFIStatusesService()
      .retrieve()
      .then(res => {
        this.rFIStatuses = res.data;
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
    this.attachmentOthersService()
      .retrieve()
      .then(res => {
        this.attachmentOthers = res.data;
      });
    this.attachmentImagesService()
      .retrieve()
      .then(res => {
        this.attachmentImages = res.data;
      });
    this.rFICommentsService()
      .retrieve()
      .then(res => {
        this.rFIComments = res.data;
      });
    this.rFITimeLineService()
      .retrieve()
      .then(res => {
        this.rFITimeLines = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
