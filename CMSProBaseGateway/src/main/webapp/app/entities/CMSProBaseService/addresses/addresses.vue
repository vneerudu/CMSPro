<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.title')" id="addresses-heading">Addresses</span>
            <router-link :to="{name: 'AddressesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-addresses">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.createLabel')">
                    Create a new Addresses
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="row">
            <div class="col-sm-12">
                <form name="searchForm" class="form-inline" v-on:submit.prevent="search(currentSearch)">
                    <div class="input-group w-100 mt-3">
                        <input type="text" class="form-control" name="currentSearch" id="currentSearch"
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.search')"
                            v-model="currentSearch" />
                        <button type="button" id="launch-search" class="btn btn-primary" v-on:click="search(currentSearch)">
                            <font-awesome-icon icon="search"></font-awesome-icon>
                        </button>
                        <button type="button" id="clear-search" class="btn btn-secondary" v-on:click="clear()"
                            v-if="currentSearch">
                            <font-awesome-icon icon="trash"></font-awesome-icon>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && addresses && addresses.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.notFound')">No addresses found</span>
        </div>
        <div class="table-responsive" v-if="addresses && addresses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('address1')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address1')">Address 1</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address1'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('address2')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address2')">Address 2</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address2'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('city')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.city')">City</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'city'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('zipCode')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.zipCode')">Zip Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'zipCode'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isActive')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.isActive')">Is Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('addressTypeDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.addressType')">Address Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'addressTypeDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stateDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.state')">State</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('countryDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.country')">Country</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('userFullName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.user')">User</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userFullName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('projectName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.project')">Project</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projectName'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="addresses in addresses"
                    :key="addresses.id">
                    <td>
                        <router-link :to="{name: 'AddressesView', params: {addressesId: addresses.id}}">{{addresses.id}}</router-link>
                    </td>
                    <td>{{addresses.address1}}</td>
                    <td>{{addresses.address2}}</td>
                    <td>{{addresses.city}}</td>
                    <td>{{addresses.zipCode}}</td>
                    <td>{{addresses.isActive}}</td>
                    <td>{{addresses.createdBy}}</td>
                    <td>{{addresses.creationDate}}</td>
                    <td>
                        <div v-if="addresses.addressTypeId">
                            <router-link :to="{name: 'AddressTypesView', params: {addressTypesId: addresses.addressTypeId}}">{{addresses.addressTypeDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="addresses.stateId">
                            <router-link :to="{name: 'StatesView', params: {statesId: addresses.stateId}}">{{addresses.stateDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="addresses.countryId">
                            <router-link :to="{name: 'CountryView', params: {countryId: addresses.countryId}}">{{addresses.countryDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="addresses.userId">
                            <router-link :to="{name: 'UsersView', params: {usersId: addresses.userId}}">{{addresses.userFullName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="addresses.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: addresses.projectId}}">{{addresses.projectName}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AddressesView', params: {addressesId: addresses.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AddressesEdit', params: {addressesId: addresses.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(addresses)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceAddresses.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-addresses-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.delete.question', {'id': removeId})">Are you sure you want to delete this Addresses?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-addresses" v-text="$t('entity.action.delete')" v-on:click="removeAddresses()">Delete</button>
            </div>
        </b-modal>
        <div v-show="addresses && addresses.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./addresses.component.ts">
</script>
