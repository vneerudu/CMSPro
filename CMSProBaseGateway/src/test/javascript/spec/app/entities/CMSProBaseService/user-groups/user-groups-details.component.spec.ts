/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UserGroupsDetailComponent from '@/entities/CMSProBaseService/user-groups/user-groups-details.vue';
import UserGroupsClass from '@/entities/CMSProBaseService/user-groups/user-groups-details.component';
import UserGroupsService from '@/entities/CMSProBaseService/user-groups/user-groups.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserGroups Management Detail Component', () => {
    let wrapper: Wrapper<UserGroupsClass>;
    let comp: UserGroupsClass;
    let userGroupsServiceStub: SinonStubbedInstance<UserGroupsService>;

    beforeEach(() => {
      userGroupsServiceStub = sinon.createStubInstance<UserGroupsService>(UserGroupsService);

      wrapper = shallowMount<UserGroupsClass>(UserGroupsDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { userGroupsService: () => userGroupsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserGroups = { id: '123' };
        userGroupsServiceStub.find.resolves(foundUserGroups);

        // WHEN
        comp.retrieveUserGroups('123');
        await comp.$nextTick();

        // THEN
        expect(comp.userGroups).toBe(foundUserGroups);
      });
    });
  });
});
