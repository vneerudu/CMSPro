import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStates } from '@/shared/model/CMSProBaseService/states.model';
import StatesService from './states.service';

@Component
export default class StatesDetails extends Vue {
  @Inject('statesService') private statesService: () => StatesService;
  public states: IStates = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.statesId) {
        vm.retrieveStates(to.params.statesId);
      }
    });
  }

  public retrieveStates(statesId) {
    this.statesService()
      .find(statesId)
      .then(res => {
        this.states = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
