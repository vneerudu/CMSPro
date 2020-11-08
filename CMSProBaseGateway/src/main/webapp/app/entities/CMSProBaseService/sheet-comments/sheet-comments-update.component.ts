import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import AlertService from '@/shared/alert/alert.service';
import { ISheetComments, SheetComments } from '@/shared/model/CMSProBaseService/sheet-comments.model';
import SheetCommentsService from './sheet-comments.service';

const validations: any = {
  sheetComments: {
    createdBy: {
      required,
    },
    comment: {
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
export default class SheetCommentsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sheetCommentsService') private sheetCommentsService: () => SheetCommentsService;
  public sheetComments: ISheetComments = new SheetComments();

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetCommentsId) {
        vm.retrieveSheetComments(to.params.sheetCommentsId);
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
    if (this.sheetComments.id) {
      this.sheetCommentsService()
        .update(this.sheetComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sheetCommentsService()
        .create(this.sheetComments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSheetComments(sheetCommentsId): void {
    this.sheetCommentsService()
      .find(sheetCommentsId)
      .then(res => {
        this.sheetComments = res;
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
