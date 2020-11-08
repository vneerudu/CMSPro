/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UserRolesDetailComponent from '@/entities/CMSProBaseService/user-roles/user-roles-details.vue';
import UserRolesClass from '@/entities/CMSProBaseService/user-roles/user-roles-details.component';
import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserRoles Management Detail Component', () => {
    let wrapper: Wrapper<UserRolesClass>;
    let comp: UserRolesClass;
    let userRolesServiceStub: SinonStubbedInstance<UserRolesService>;

    beforeEach(() => {
      userRolesServiceStub = sinon.createStubInstance<UserRolesService>(UserRolesService);

      wrapper = shallowMount<UserRolesClass>(UserRolesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { userRolesService: () => userRolesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserRoles = { id: '123' };
        userRolesServiceStub.find.resolves(foundUserRoles);

        // WHEN
        comp.retrieveUserRoles('123');
        await comp.$nextTick();

        // THEN
        expect(comp.userRoles).toBe(foundUserRoles);
      });
    });
  });
});
