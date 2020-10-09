import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProjectStatuses } from '@/shared/model/CMSProMicroService/project-statuses.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ProjectStatusesService from './project-statuses.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ProjectStatuses extends mixins(AlertMixin) {
  @Inject('projectStatusesService') private projectStatusesService: () => ProjectStatusesService;
  public currentSearch = '';
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public projectStatuses: IProjectStatuses[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProjectStatusess();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllProjectStatusess();
  }

  public clear(): void {
    this.currentSearch = '';
    this.page = 1;
    this.retrieveAllProjectStatusess();
  }

  public retrieveAllProjectStatusess(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.currentSearch) {
      this.projectStatusesService()
        .search(this.currentSearch, paginationQuery)
        .then(
          res => {
            this.projectStatuses = res.data;
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
    this.projectStatusesService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.projectStatuses = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IProjectStatuses): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProjectStatuses(): void {
    this.projectStatusesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllProjectStatusess();
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
    this.retrieveAllProjectStatusess();
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
