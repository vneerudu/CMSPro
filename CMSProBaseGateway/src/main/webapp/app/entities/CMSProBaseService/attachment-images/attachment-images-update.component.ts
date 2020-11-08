import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AttachmentsService from '../attachments/attachments.service';
import { IAttachments } from '@/shared/model/CMSProBaseService/attachments.model';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { IAttachmentImages, AttachmentImages } from '@/shared/model/CMSProBaseService/attachment-images.model';
import AttachmentImagesService from './attachment-images.service';

const validations: any = {
  attachmentImages: {
    fileName: {
      required,
    },
    fileType: {},
    content: {},
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
export default class AttachmentImagesUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('attachmentImagesService') private attachmentImagesService: () => AttachmentImagesService;
  public attachmentImages: IAttachmentImages = new AttachmentImages();

  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;

  public attachments: IAttachments[] = [];

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentImagesId) {
        vm.retrieveAttachmentImages(to.params.attachmentImagesId);
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
    if (this.attachmentImages.id) {
      this.attachmentImagesService()
        .update(this.attachmentImages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.attachmentImagesService()
        .create(this.attachmentImages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAttachmentImages(attachmentImagesId): void {
    this.attachmentImagesService()
      .find(attachmentImagesId)
      .then(res => {
        this.attachmentImages = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.attachmentsService()
      .retrieve()
      .then(res => {
        this.attachments = res.data;
      });
    this.rFIService()
      .retrieve()
      .then(res => {
        this.rFIS = res.data;
      });
  }
}
