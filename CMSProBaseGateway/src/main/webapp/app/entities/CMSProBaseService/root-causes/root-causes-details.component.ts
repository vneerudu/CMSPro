import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRootCauses } from '@/shared/model/CMSProBaseService/root-causes.model';
import RootCausesService from './root-causes.service';

@Component
export default class RootCausesDetails extends Vue {
  @Inject('rootCausesService') private rootCausesService: () => RootCausesService;
  public rootCauses: IRootCauses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rootCausesId) {
        vm.retrieveRootCauses(to.params.rootCausesId);
      }
    });
  }

  public retrieveRootCauses(rootCausesId) {
    this.rootCausesService()
      .find(rootCausesId)
      .then(res => {
        this.rootCauses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
