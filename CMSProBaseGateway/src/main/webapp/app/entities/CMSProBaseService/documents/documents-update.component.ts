import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocuments, Documents } from '@/shared/model/CMSProBaseService/documents.model';
import DocumentsService from './documents.service';

const validations: any = {
  documents: {
    fileName: {
      required,
    },
    fileType: {},
    content: {
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
export default class DocumentsUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentsService') private documentsService: () => DocumentsService;
  public documents: IDocuments = new Documents();

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentsId) {
        vm.retrieveDocuments(to.params.documentsId);
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
    if (this.documents.id) {
      this.documentsService()
        .update(this.documents)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentsService()
        .create(this.documents)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocuments(documentsId): void {
    this.documentsService()
      .find(documentsId)
      .then(res => {
        this.documents = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.sheetsService()
      .retrieve()
      .then(res => {
        this.sheets = res.data;
      });
  }
}
