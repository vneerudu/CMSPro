import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import AlertService from '@/shared/alert/alert.service';
import { ISheetHistory, SheetHistory } from '@/shared/model/CMSProBaseService/sheet-history.model';
import SheetHistoryService from './sheet-history.service';

const validations: any = {
  sheetHistory: {
    number: {
      required,
      numeric,
    },
    version: {},
    isActive: {
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
export default class SheetHistoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sheetHistoryService') private sheetHistoryService: () => SheetHistoryService;
  public sheetHistory: ISheetHistory = new SheetHistory();

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetHistoryId) {
        vm.retrieveSheetHistory(to.params.sheetHistoryId);
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
    if (this.sheetHistory.id) {
      this.sheetHistoryService()
        .update(this.sheetHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sheetHistoryService()
        .create(this.sheetHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSheetHistory(sheetHistoryId): void {
    this.sheetHistoryService()
      .find(sheetHistoryId)
      .then(res => {
        this.sheetHistory = res;
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
