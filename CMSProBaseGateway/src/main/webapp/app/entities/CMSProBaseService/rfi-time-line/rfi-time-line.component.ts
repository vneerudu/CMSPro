import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRFITimeLine } from '@/shared/model/CMSProBaseService/rfi-time-line.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RFITimeLineService from './rfi-time-line.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RFITimeLine extends mixins(AlertMixin) {
  @Inject('rFITimeLineService') private rFITimeLineService: () => RFITimeLineService;
  public currentSearch = '';
  private removeId: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public rFITimeLines: IRFITimeLine[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRFITimeLines();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllRFITimeLines();
  }

  public clear(): void {
    this.currentSearch = '';
    this.page = 1;
    this.retrieveAllRFITimeLines();
  }

  public retrieveAllRFITimeLines(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.currentSearch) {
      this.rFITimeLineService()
        .search(this.currentSearch, paginationQuery)
        .then(
          res => {
            this.rFITimeLines = res.data;
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
    this.rFITimeLineService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.rFITimeLines = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRFITimeLine): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRFITimeLine(): void {
    this.rFITimeLineService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRFITimeLines();
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
    this.retrieveAllRFITimeLines();
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
