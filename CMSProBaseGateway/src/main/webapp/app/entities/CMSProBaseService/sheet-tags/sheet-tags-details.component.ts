import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISheetTags } from '@/shared/model/CMSProBaseService/sheet-tags.model';
import SheetTagsService from './sheet-tags.service';

@Component
export default class SheetTagsDetails extends Vue {
  @Inject('sheetTagsService') private sheetTagsService: () => SheetTagsService;
  public sheetTags: ISheetTags = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetTagsId) {
        vm.retrieveSheetTags(to.params.sheetTagsId);
      }
    });
  }

  public retrieveSheetTags(sheetTagsId) {
    this.sheetTagsService()
      .find(sheetTagsId)
      .then(res => {
        this.sheetTags = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
