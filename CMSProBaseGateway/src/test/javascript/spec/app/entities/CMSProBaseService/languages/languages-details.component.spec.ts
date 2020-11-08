/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LanguagesDetailComponent from '@/entities/CMSProBaseService/languages/languages-details.vue';
import LanguagesClass from '@/entities/CMSProBaseService/languages/languages-details.component';
import LanguagesService from '@/entities/CMSProBaseService/languages/languages.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Languages Management Detail Component', () => {
    let wrapper: Wrapper<LanguagesClass>;
    let comp: LanguagesClass;
    let languagesServiceStub: SinonStubbedInstance<LanguagesService>;

    beforeEach(() => {
      languagesServiceStub = sinon.createStubInstance<LanguagesService>(LanguagesService);

      wrapper = shallowMount<LanguagesClass>(LanguagesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { languagesService: () => languagesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLanguages = { id: '123' };
        languagesServiceStub.find.resolves(foundLanguages);

        // WHEN
        comp.retrieveLanguages('123');
        await comp.$nextTick();

        // THEN
        expect(comp.languages).toBe(foundLanguages);
      });
    });
  });
});
