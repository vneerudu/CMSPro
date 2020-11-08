import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import AddressTypesService from '../address-types/address-types.service';
import { IAddressTypes } from '@/shared/model/CMSProBaseService/address-types.model';

import StatesService from '../states/states.service';
import { IStates } from '@/shared/model/CMSProBaseService/states.model';

import CountryService from '../country/country.service';
import { ICountry } from '@/shared/model/CMSProBaseService/country.model';

import UsersService from '../users/users.service';
import { IUsers } from '@/shared/model/CMSProBaseService/users.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { IAddresses, Addresses } from '@/shared/model/CMSProBaseService/addresses.model';
import AddressesService from './addresses.service';

const validations: any = {
  addresses: {
    address1: {},
    address2: {},
    city: {},
    zipCode: {},
    isActive: {},
    createdBy: {
      required,
    },
    creationDate: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class AddressesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('addressesService') private addressesService: () => AddressesService;
  public addresses: IAddresses = new Addresses();

  @Inject('addressTypesService') private addressTypesService: () => AddressTypesService;

  public addressTypes: IAddressTypes[] = [];

  @Inject('statesService') private statesService: () => StatesService;

  public states: IStates[] = [];

  @Inject('countryService') private countryService: () => CountryService;

  public countries: ICountry[] = [];

  @Inject('usersService') private usersService: () => UsersService;

  public users: IUsers[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressesId) {
        vm.retrieveAddresses(to.params.addressesId);
      }
      vm.initRelationships();
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
    if (this.addresses.id) {
      this.addressesService()
        .update(this.addresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.addressesService()
        .create(this.addresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAddresses(addressesId): void {
    this.addressesService()
      .find(addressesId)
      .then(res => {
        this.addresses = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.addressTypesService()
      .retrieve()
      .then(res => {
        this.addressTypes = res.data;
      });
    this.statesService()
      .retrieve()
      .then(res => {
        this.states = res.data;
      });
    this.countryService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
    this.usersService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
