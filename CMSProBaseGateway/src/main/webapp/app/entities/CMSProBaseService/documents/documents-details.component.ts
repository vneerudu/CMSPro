import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDocuments } from '@/shared/model/CMSProBaseService/documents.model';
import DocumentsService from './documents.service';

@Component
export default class DocumentsDetails extends mixins(JhiDataUtils) {
  @Inject('documentsService') private documentsService: () => DocumentsService;
  public documents: IDocuments = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentsId) {
        vm.retrieveDocuments(to.params.documentsId);
      }
    });
  }

  public retrieveDocuments(documentsId) {
    this.documentsService()
      .find(documentsId)
      .then(res => {
        this.documents = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
