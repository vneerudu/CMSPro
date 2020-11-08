import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISheetHistory } from '@/shared/model/CMSProBaseService/sheet-history.model';
import SheetHistoryService from './sheet-history.service';

@Component
export default class SheetHistoryDetails extends Vue {
  @Inject('sheetHistoryService') private sheetHistoryService: () => SheetHistoryService;
  public sheetHistory: ISheetHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetHistoryId) {
        vm.retrieveSheetHistory(to.params.sheetHistoryId);
      }
    });
  }

  public retrieveSheetHistory(sheetHistoryId) {
    this.sheetHistoryService()
      .find(sheetHistoryId)
      .then(res => {
        this.sheetHistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
