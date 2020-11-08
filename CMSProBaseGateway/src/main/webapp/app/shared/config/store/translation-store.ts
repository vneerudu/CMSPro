import { Module } from 'vuex';

export const translationStore: Module<any, any> = {
  state: {
    currentLanguage: localStorage.getItem('currentLanguage') || 'en',
    languages: {
      'ar-ly': { name: 'العربية', rtl: true },
      'zh-cn': { name: '中文（简体）' },
      en: { name: 'English' },
      fr: { name: 'Français' },
      el: { name: 'Ελληνικά' },
      hi: { name: 'हिंदी' },
      it: { name: 'Italiano' },
      ja: { name: '日本語' },
      ko: { name: '한국어' },
      'pt-pt': { name: 'Português' },
      ru: { name: 'Русский' },
      es: { name: 'Español' },
      te: { name: 'తెలుగు' },
      vi: { name: 'Tiếng Việt' },
      // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    },
  },
  getters: {
    currentLanguage: state => state.currentLanguage,
    languages: state => state.languages,
  },
  mutations: {
    currentLanguage(state, newLanguage) {
      state.currentLanguage = newLanguage;
      localStorage.setItem('currentLanguage', newLanguage);
    },
  },
};
