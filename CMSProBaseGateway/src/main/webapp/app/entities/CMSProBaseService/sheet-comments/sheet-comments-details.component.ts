import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISheetComments } from '@/shared/model/CMSProBaseService/sheet-comments.model';
import SheetCommentsService from './sheet-comments.service';

@Component
export default class SheetCommentsDetails extends Vue {
  @Inject('sheetCommentsService') private sheetCommentsService: () => SheetCommentsService;
  public sheetComments: ISheetComments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetCommentsId) {
        vm.retrieveSheetComments(to.params.sheetCommentsId);
      }
    });
  }

  public retrieveSheetComments(sheetCommentsId) {
    this.sheetCommentsService()
      .find(sheetCommentsId)
      .then(res => {
        this.sheetComments = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
