import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRootCauseGroups } from '@/shared/model/CMSProBaseService/root-cause-groups.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RootCauseGroupsService from './root-cause-groups.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RootCauseGroups extends mixins(AlertMixin) {
  @Inject('rootCauseGroupsService') private rootCauseGroupsService: () => RootCauseGroupsService;
  public currentSearch = '';
  private removeId: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public rootCauseGroups: IRootCauseGroups[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRootCauseGroupss();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllRootCauseGroupss();
  }

  public clear(): void {
    this.currentSearch = '';
    this.page = 1;
    this.retrieveAllRootCauseGroupss();
  }

  public retrieveAllRootCauseGroupss(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.currentSearch) {
      this.rootCauseGroupsService()
        .search(this.currentSearch, paginationQuery)
        .then(
          res => {
            this.rootCauseGroups = res.data;
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
    this.rootCauseGroupsService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.rootCauseGroups = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRootCauseGroups): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRootCauseGroups(): void {
    this.rootCauseGroupsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRootCauseGroupss();
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
    this.retrieveAllRootCauseGroupss();
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
