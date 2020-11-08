import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILists } from '@/shared/model/CMSProBaseService/lists.model';
import ListsService from './lists.service';

@Component
export default class ListsDetails extends Vue {
  @Inject('listsService') private listsService: () => ListsService;
  public lists: ILists = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listsId) {
        vm.retrieveLists(to.params.listsId);
      }
    });
  }

  public retrieveLists(listsId) {
    this.listsService()
      .find(listsId)
      .then(res => {
        this.lists = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
