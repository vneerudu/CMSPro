<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.title')" id="rfi-comments-heading">RFI Comments</span>
            <router-link :to="{name: 'RFICommentsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-rfi-comments">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.createLabel')">
                    Create a new RFI Comments
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && rFIComments && rFIComments.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.notFound')">No rFIComments found</span>
        </div>
        <div class="table-responsive" v-if="rFIComments && rFIComments.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('comment')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.comment')">Comment</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'comment'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rfiTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.rfi')">Rfi</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rfiTitle'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rFIComments in rFIComments"
                    :key="rFIComments.id">
                    <td>
                        <router-link :to="{name: 'RFICommentsView', params: {rFICommentsId: rFIComments.id}}">{{rFIComments.id}}</router-link>
                    </td>
                    <td>{{rFIComments.createdBy}}</td>
                    <td>{{rFIComments.comment}}</td>
                    <td>{{rFIComments.creationDate}}</td>
                    <td>
                        <div v-if="rFIComments.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: rFIComments.rfiId}}">{{rFIComments.rfiTitle}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RFICommentsView', params: {rFICommentsId: rFIComments.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RFICommentsEdit', params: {rFICommentsId: rFIComments.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(rFIComments)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-rFIComments-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.delete.question', {'id': removeId})">Are you sure you want to delete this RFI Comments?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-rFIComments" v-text="$t('entity.action.delete')" v-on:click="removeRFIComments()">Delete</button>
            </div>
        </b-modal>
        <div v-show="rFIComments && rFIComments.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi-comments.component.ts">
</script>
