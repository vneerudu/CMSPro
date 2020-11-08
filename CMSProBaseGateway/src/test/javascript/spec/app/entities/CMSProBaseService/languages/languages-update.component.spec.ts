/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LanguagesUpdateComponent from '@/entities/CMSProBaseService/languages/languages-update.vue';
import LanguagesClass from '@/entities/CMSProBaseService/languages/languages-update.component';
import LanguagesService from '@/entities/CMSProBaseService/languages/languages.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Languages Management Update Component', () => {
    let wrapper: Wrapper<LanguagesClass>;
    let comp: LanguagesClass;
    let languagesServiceStub: SinonStubbedInstance<LanguagesService>;

    beforeEach(() => {
      languagesServiceStub = sinon.createStubInstance<LanguagesService>(LanguagesService);

      wrapper = shallowMount<LanguagesClass>(LanguagesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          languagesService: () => languagesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.languages = entity;
        languagesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languagesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.languages = entity;
        languagesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languagesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
