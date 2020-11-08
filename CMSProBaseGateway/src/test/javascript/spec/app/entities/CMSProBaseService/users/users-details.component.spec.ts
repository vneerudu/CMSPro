/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UsersDetailComponent from '@/entities/CMSProBaseService/users/users-details.vue';
import UsersClass from '@/entities/CMSProBaseService/users/users-details.component';
import UsersService from '@/entities/CMSProBaseService/users/users.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Users Management Detail Component', () => {
    let wrapper: Wrapper<UsersClass>;
    let comp: UsersClass;
    let usersServiceStub: SinonStubbedInstance<UsersService>;

    beforeEach(() => {
      usersServiceStub = sinon.createStubInstance<UsersService>(UsersService);

      wrapper = shallowMount<UsersClass>(UsersDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { usersService: () => usersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUsers = { id: '123' };
        usersServiceStub.find.resolves(foundUsers);

        // WHEN
        comp.retrieveUsers('123');
        await comp.$nextTick();

        // THEN
        expect(comp.users).toBe(foundUsers);
      });
    });
  });
});
