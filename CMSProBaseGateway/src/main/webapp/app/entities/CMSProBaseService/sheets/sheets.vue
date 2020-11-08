<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.title')" id="sheets-heading">Sheets</span>
            <router-link :to="{name: 'SheetsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-sheets">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.createLabel')">
                    Create a new Sheets
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && sheets && sheets.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.notFound')">No sheets found</span>
        </div>
        <div class="table-responsive" v-if="sheets && sheets.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('number')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.number')">Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'number'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('version')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.version')">Version</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'version'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('documentsId')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.documents')">Documents</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'documentsId'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('projectName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.project')">Project</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projectName'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="sheets in sheets"
                    :key="sheets.id">
                    <td>
                        <router-link :to="{name: 'SheetsView', params: {sheetsId: sheets.id}}">{{sheets.id}}</router-link>
                    </td>
                    <td>{{sheets.number}}</td>
                    <td>{{sheets.title}}</td>
                    <td>{{sheets.version}}</td>
                    <td>{{sheets.createdBy}}</td>
                    <td>{{sheets.creationDate}}</td>
                    <td>
                        <div v-if="sheets.documentsId">
                            <router-link :to="{name: 'DocumentsView', params: {documentsId: sheets.documentsId}}">{{sheets.documentsId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="sheets.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: sheets.projectId}}">{{sheets.projectName}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: sheets.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SheetsEdit', params: {sheetsId: sheets.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(sheets)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceSheets.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-sheets-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.delete.question', {'id': removeId})">Are you sure you want to delete this Sheets?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-sheets" v-text="$t('entity.action.delete')" v-on:click="removeSheets()">Delete</button>
            </div>
        </b-modal>
        <div v-show="sheets && sheets.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheets.component.ts">
</script>
