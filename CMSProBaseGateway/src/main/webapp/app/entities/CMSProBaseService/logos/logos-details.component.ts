import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ILogos } from '@/shared/model/CMSProBaseService/logos.model';
import LogosService from './logos.service';

@Component
export default class LogosDetails extends mixins(JhiDataUtils) {
  @Inject('logosService') private logosService: () => LogosService;
  public logos: ILogos = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.logosId) {
        vm.retrieveLogos(to.params.logosId);
      }
    });
  }

  public retrieveLogos(logosId) {
    this.logosService()
      .find(logosId)
      .then(res => {
        this.logos = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
