import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AttachmentImagesService from '../attachment-images/attachment-images.service';
import { IAttachmentImages } from '@/shared/model/CMSProBaseService/attachment-images.model';

import AttachmentOthersService from '../attachment-others/attachment-others.service';
import { IAttachmentOthers } from '@/shared/model/CMSProBaseService/attachment-others.model';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import AlertService from '@/shared/alert/alert.service';
import { IAttachments, Attachments } from '@/shared/model/CMSProBaseService/attachments.model';
import AttachmentsService from './attachments.service';

const validations: any = {
  attachments: {
    folder: {},
    fileName: {
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
export default class AttachmentsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;
  public attachments: IAttachments = new Attachments();

  @Inject('attachmentImagesService') private attachmentImagesService: () => AttachmentImagesService;

  public attachmentImages: IAttachmentImages[] = [];

  @Inject('attachmentOthersService') private attachmentOthersService: () => AttachmentOthersService;

  public attachmentOthers: IAttachmentOthers[] = [];

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentsId) {
        vm.retrieveAttachments(to.params.attachmentsId);
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
    if (this.attachments.id) {
      this.attachmentsService()
        .update(this.attachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.attachmentsService()
        .create(this.attachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAttachments(attachmentsId): void {
    this.attachmentsService()
      .find(attachmentsId)
      .then(res => {
        this.attachments = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.attachmentImagesService()
      .retrieve()
      .then(res => {
        this.attachmentImages = res.data;
      });
    this.attachmentOthersService()
      .retrieve()
      .then(res => {
        this.attachmentOthers = res.data;
      });
    this.sheetsService()
      .retrieve()
      .then(res => {
        this.sheets = res.data;
      });
  }
}
