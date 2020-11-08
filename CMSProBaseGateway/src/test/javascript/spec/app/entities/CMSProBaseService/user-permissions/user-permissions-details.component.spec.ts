/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UserPermissionsDetailComponent from '@/entities/CMSProBaseService/user-permissions/user-permissions-details.vue';
import UserPermissionsClass from '@/entities/CMSProBaseService/user-permissions/user-permissions-details.component';
import UserPermissionsService from '@/entities/CMSProBaseService/user-permissions/user-permissions.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserPermissions Management Detail Component', () => {
    let wrapper: Wrapper<UserPermissionsClass>;
    let comp: UserPermissionsClass;
    let userPermissionsServiceStub: SinonStubbedInstance<UserPermissionsService>;

    beforeEach(() => {
      userPermissionsServiceStub = sinon.createStubInstance<UserPermissionsService>(UserPermissionsService);

      wrapper = shallowMount<UserPermissionsClass>(UserPermissionsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { userPermissionsService: () => userPermissionsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserPermissions = { id: '123' };
        userPermissionsServiceStub.find.resolves(foundUserPermissions);

        // WHEN
        comp.retrieveUserPermissions('123');
        await comp.$nextTick();

        // THEN
        expect(comp.userPermissions).toBe(foundUserPermissions);
      });
    });
  });
});
