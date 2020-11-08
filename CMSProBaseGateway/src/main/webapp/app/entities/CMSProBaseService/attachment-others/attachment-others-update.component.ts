import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AttachmentsService from '../attachments/attachments.service';
import { IAttachments } from '@/shared/model/CMSProBaseService/attachments.model';

import RFIService from '../rfi/rfi.service';
import { IRFI } from '@/shared/model/CMSProBaseService/rfi.model';

import AlertService from '@/shared/alert/alert.service';
import { IAttachmentOthers, AttachmentOthers } from '@/shared/model/CMSProBaseService/attachment-others.model';
import AttachmentOthersService from './attachment-others.service';

const validations: any = {
  attachmentOthers: {
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
export default class AttachmentOthersUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('attachmentOthersService') private attachmentOthersService: () => AttachmentOthersService;
  public attachmentOthers: IAttachmentOthers = new AttachmentOthers();

  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;

  public attachments: IAttachments[] = [];

  @Inject('rFIService') private rFIService: () => RFIService;

  public rFIS: IRFI[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentOthersId) {
        vm.retrieveAttachmentOthers(to.params.attachmentOthersId);
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
    if (this.attachmentOthers.id) {
      this.attachmentOthersService()
        .update(this.attachmentOthers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.attachmentOthersService()
        .create(this.attachmentOthers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAttachmentOthers(attachmentOthersId): void {
    this.attachmentOthersService()
      .find(attachmentOthersId)
      .then(res => {
        this.attachmentOthers = res;
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
