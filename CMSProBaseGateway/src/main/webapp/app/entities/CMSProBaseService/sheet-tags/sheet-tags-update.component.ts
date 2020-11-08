import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import SheetsService from '../sheets/sheets.service';
import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';

import AlertService from '@/shared/alert/alert.service';
import { ISheetTags, SheetTags } from '@/shared/model/CMSProBaseService/sheet-tags.model';
import SheetTagsService from './sheet-tags.service';

const validations: any = {
  sheetTags: {
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
export default class SheetTagsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sheetTagsService') private sheetTagsService: () => SheetTagsService;
  public sheetTags: ISheetTags = new SheetTags();

  @Inject('sheetsService') private sheetsService: () => SheetsService;

  public sheets: ISheets[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetTagsId) {
        vm.retrieveSheetTags(to.params.sheetTagsId);
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
    if (this.sheetTags.id) {
      this.sheetTagsService()
        .update(this.sheetTags)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sheetTagsService()
        .create(this.sheetTags)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSheetTags(sheetTagsId): void {
    this.sheetTagsService()
      .find(sheetTagsId)
      .then(res => {
        this.sheetTags = res;
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
