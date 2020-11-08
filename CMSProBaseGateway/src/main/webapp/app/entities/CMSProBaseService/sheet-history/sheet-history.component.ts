import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISheetHistory } from '@/shared/model/CMSProBaseService/sheet-history.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import SheetHistoryService from './sheet-history.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SheetHistory extends mixins(AlertMixin) {
  @Inject('sheetHistoryService') private sheetHistoryService: () => SheetHistoryService;
  public currentSearch = '';
  private removeId: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public sheetHistories: ISheetHistory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSheetHistorys();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllSheetHistorys();
  }

  public clear(): void {
    this.currentSearch = '';
    this.page = 1;
    this.retrieveAllSheetHistorys();
  }

  public retrieveAllSheetHistorys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.currentSearch) {
      this.sheetHistoryService()
        .search(this.currentSearch, paginationQuery)
        .then(
          res => {
            this.sheetHistories = res.data;
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
          }
        );
      return;
    }
    this.sheetHistoryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.sheetHistories = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISheetHistory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSheetHistory(): void {
    this.sheetHistoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllSheetHistorys();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllSheetHistorys();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
