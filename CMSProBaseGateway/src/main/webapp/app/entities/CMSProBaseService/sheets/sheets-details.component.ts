import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISheets } from '@/shared/model/CMSProBaseService/sheets.model';
import SheetsService from './sheets.service';

@Component
export default class SheetsDetails extends Vue {
  @Inject('sheetsService') private sheetsService: () => SheetsService;
  public sheets: ISheets = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetsId) {
        vm.retrieveSheets(to.params.sheetsId);
      }
    });
  }

  public retrieveSheets(sheetsId) {
    this.sheetsService()
      .find(sheetsId)
      .then(res => {
        this.sheets = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
