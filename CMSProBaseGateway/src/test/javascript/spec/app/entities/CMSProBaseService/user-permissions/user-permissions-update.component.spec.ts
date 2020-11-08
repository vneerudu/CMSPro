/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserPermissionsUpdateComponent from '@/entities/CMSProBaseService/user-permissions/user-permissions-update.vue';
import UserPermissionsClass from '@/entities/CMSProBaseService/user-permissions/user-permissions-update.component';
import UserPermissionsService from '@/entities/CMSProBaseService/user-permissions/user-permissions.service';

import UserRolesService from '@/entities/CMSProBaseService/user-roles/user-roles.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('UserPermissions Management Update Component', () => {
    let wrapper: Wrapper<UserPermissionsClass>;
    let comp: UserPermissionsClass;
    let userPermissionsServiceStub: SinonStubbedInstance<UserPermissionsService>;

    beforeEach(() => {
      userPermissionsServiceStub = sinon.createStubInstance<UserPermissionsService>(UserPermissionsService);

      wrapper = shallowMount<UserPermissionsClass>(UserPermissionsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          userPermissionsService: () => userPermissionsServiceStub,

          userRolesService: () => new UserRolesService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '123' };
        comp.userPermissions = entity;
        userPermissionsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userPermissionsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.userPermissions = entity;
        userPermissionsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(userPermissionsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
