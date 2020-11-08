import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ILanguages, Languages } from '@/shared/model/CMSProBaseService/languages.model';
import LanguagesService from './languages.service';

const validations: any = {
  languages: {
    code: {
      required,
    },
    description: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class LanguagesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('languagesService') private languagesService: () => LanguagesService;
  public languages: ILanguages = new Languages();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.languagesId) {
        vm.retrieveLanguages(to.params.languagesId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.languages.id) {
      this.languagesService()
        .update(this.languages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceLanguages.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.languagesService()
        .create(this.languages)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceLanguages.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLanguages(languagesId): void {
    this.languagesService()
      .find(languagesId)
      .then(res => {
        this.languages = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
