<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.title')" id="sheet-history-heading">Sheet Histories</span>
            <router-link :to="{name: 'SheetHistoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sheet-history">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.createLabel')">
                    Create a new Sheet History
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && sheetHistories && sheetHistories.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.notFound')">No sheetHistories found</span>
        </div>
        <div class="table-responsive" v-if="sheetHistories && sheetHistories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('number')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.number')">Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'number'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('version')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.version')">Version</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'version'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isActive')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.isActive')">Is Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sheetNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.sheet')">Sheet</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sheetNumber'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sheetHistory in sheetHistories"
                    :key="sheetHistory.id">
                    <td>
                        <router-link :to="{name: 'SheetHistoryView', params: {sheetHistoryId: sheetHistory.id}}">{{sheetHistory.id}}</router-link>
                    </td>
                    <td>{{sheetHistory.number}}</td>
                    <td>{{sheetHistory.version}}</td>
                    <td>{{sheetHistory.isActive}}</td>
                    <td>{{sheetHistory.createdBy}}</td>
                    <td>{{sheetHistory.creationDate}}</td>
                    <td>
                        <div v-if="sheetHistory.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: sheetHistory.sheetId}}">{{sheetHistory.sheetNumber}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SheetHistoryView', params: {sheetHistoryId: sheetHistory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SheetHistoryEdit', params: {sheetHistoryId: sheetHistory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sheetHistory)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sheetHistory-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.delete.question', {'id': removeId})">Are you sure you want to delete this Sheet History?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sheetHistory" v-text="$t('entity.action.delete')" v-on:click="removeSheetHistory()">Delete</button>
            </div>
        </b-modal>
        <div v-show="sheetHistories && sheetHistories.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheet-history.component.ts">
</script>
