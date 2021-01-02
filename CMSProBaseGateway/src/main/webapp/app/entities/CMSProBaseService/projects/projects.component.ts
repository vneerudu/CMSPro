import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ProjectsService from './projects.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Projects extends mixins(AlertMixin) {
  @Inject('projectsService') private projectsService: () => ProjectsService;
  public currentSearch = '';
  private removeId: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public projects: IProjects[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProjectss();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllProjectss();
  }

  public clear(): void {
    this.currentSearch = '';
    this.page = 1;
    this.retrieveAllProjectss();
  }

  public retrieveAllProjectss(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.currentSearch) {
      this.projectsService()
        .search(this.currentSearch, paginationQuery)
        .then(
          res => {
            this.projects = res.data;
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
    this.projectsService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.projects = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IProjects): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProjects(): void {
    this.projectsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllProjectss();
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
    this.retrieveAllProjectss();
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