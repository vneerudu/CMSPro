import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILanguages } from '@/shared/model/CMSProBaseService/languages.model';
import LanguagesService from './languages.service';

@Component
export default class LanguagesDetails extends Vue {
  @Inject('languagesService') private languagesService: () => LanguagesService;
  public languages: ILanguages = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.languagesId) {
        vm.retrieveLanguages(to.params.languagesId);
      }
    });
  }

  public retrieveLanguages(languagesId) {
    this.languagesService()
      .find(languagesId)
      .then(res => {
        this.languages = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
