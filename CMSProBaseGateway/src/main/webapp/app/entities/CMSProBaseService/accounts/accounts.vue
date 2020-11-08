<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.title')" id="accounts-heading">Accounts</span>
            <router-link :to="{name: 'AccountsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-accounts">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.createLabel')">
                    Create a new Accounts
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && accounts && accounts.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.notFound')">No accounts found</span>
        </div>
        <div class="table-responsive" v-if="accounts && accounts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('accountNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.accountNumber')">Account Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accountNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('firstName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.firstName')">First Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lastName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.lastName')">Last Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('emailAddress')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.emailAddress')">Email Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'emailAddress'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phoneNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.phoneNumber')">Phone Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('languageCode')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.language')">Language</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'languageCode'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('logoFileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.logo')">Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logoFileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('statusDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statusDescription'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="accounts in accounts"
                    :key="accounts.id">
                    <td>
                        <router-link :to="{name: 'AccountsView', params: {accountsId: accounts.id}}">{{accounts.id}}</router-link>
                    </td>
                    <td>{{accounts.accountNumber}}</td>
                    <td>{{accounts.firstName}}</td>
                    <td>{{accounts.lastName}}</td>
                    <td>{{accounts.emailAddress}}</td>
                    <td>{{accounts.phoneNumber}}</td>
                    <td>{{accounts.creationDate}}</td>
                    <td>{{accounts.createdBy}}</td>
                    <td>
                        <div v-if="accounts.languageId">
                            <router-link :to="{name: 'LanguagesView', params: {languagesId: accounts.languageId}}">{{accounts.languageCode}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="accounts.logoId">
                            <router-link :to="{name: 'LogosView', params: {logosId: accounts.logoId}}">{{accounts.logoFileName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="accounts.statusId">
                            <router-link :to="{name: 'AccountStatusesView', params: {accountStatusesId: accounts.statusId}}">{{accounts.statusDescription}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: accounts.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AccountsEdit', params: {accountsId: accounts.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(accounts)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceAccounts.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-accounts-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.delete.question', {'id': removeId})">Are you sure you want to delete this Accounts?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-accounts" v-text="$t('entity.action.delete')" v-on:click="removeAccounts()">Delete</button>
            </div>
        </b-modal>
        <div v-show="accounts && accounts.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./accounts.component.ts">
</script>
