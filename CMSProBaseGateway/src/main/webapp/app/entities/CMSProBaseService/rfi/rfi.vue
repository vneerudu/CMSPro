<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.title')" id="rfi-heading">RFIS</span>
            <router-link :to="{name: 'RFICreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-rfi">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.createLabel')">
                    Create a new RFI
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && rFIS && rFIS.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.notFound')">No rFIS found</span>
        </div>
        <div class="table-responsive" v-if="rFIS && rFIS.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('question')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.question')">Question</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'question'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('answer')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.answer')">Answer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'answer'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sentDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.sentDate')">Sent Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sentDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('dueDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.dueDate')">Due Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dueDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('locked')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.locked')">Locked</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locked'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lockedBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.lockedBy')">Locked By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lockedBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('statusDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statusDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('projectName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.project')">Project</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projectName'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rFI in rFIS"
                    :key="rFI.id">
                    <td>
                        <router-link :to="{name: 'RFIView', params: {rFIId: rFI.id}}">{{rFI.id}}</router-link>
                    </td>
                    <td>{{rFI.title}}</td>
                    <td>{{rFI.question}}</td>
                    <td>{{rFI.answer}}</td>
                    <td>{{rFI.sentDate}}</td>
                    <td>{{rFI.dueDate}}</td>
                    <td>{{rFI.locked}}</td>
                    <td>{{rFI.lockedBy}}</td>
                    <td>
                        <div v-if="rFI.statusId">
                            <router-link :to="{name: 'RFIStatusesView', params: {rFIStatusesId: rFI.statusId}}">{{rFI.statusDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="rFI.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: rFI.projectId}}">{{rFI.projectName}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RFIView', params: {rFIId: rFI.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RFIEdit', params: {rFIId: rFI.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(rFI)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceRFi.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-rFI-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.delete.question', {'id': removeId})">Are you sure you want to delete this RFI?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-rFI" v-text="$t('entity.action.delete')" v-on:click="removeRFI()">Delete</button>
            </div>
        </b-modal>
        <div v-show="rFIS && rFIS.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi.component.ts">
</script>
