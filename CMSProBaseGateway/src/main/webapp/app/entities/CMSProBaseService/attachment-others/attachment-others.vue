<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.title')" id="attachment-others-heading">Attachment Others</span>
            <router-link :to="{name: 'AttachmentOthersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-attachment-others">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.createLabel')">
                    Create a new Attachment Others
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && attachmentOthers && attachmentOthers.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.notFound')">No attachmentOthers found</span>
        </div>
        <div class="table-responsive" v-if="attachmentOthers && attachmentOthers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileName')">File Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileType')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileType')">File Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rfiTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.rfi')">Rfi</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rfiTitle'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="attachmentOthers in attachmentOthers"
                    :key="attachmentOthers.id">
                    <td>
                        <router-link :to="{name: 'AttachmentOthersView', params: {attachmentOthersId: attachmentOthers.id}}">{{attachmentOthers.id}}</router-link>
                    </td>
                    <td>{{attachmentOthers.fileName}}</td>
                    <td>{{attachmentOthers.fileType}}</td>
                    <td>
                        <a v-if="attachmentOthers.content" v-on:click="openFile(attachmentOthers.contentContentType, attachmentOthers.content)" v-text="$t('entity.action.open')">open</a>
                        <span v-if="attachmentOthers.content">{{attachmentOthers.contentContentType}}, {{byteSize(attachmentOthers.content)}}</span>
                    </td>
                    <td>{{attachmentOthers.createdBy}}</td>
                    <td>{{attachmentOthers.creationDate}}</td>
                    <td>
                        <div v-if="attachmentOthers.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: attachmentOthers.rfiId}}">{{attachmentOthers.rfiTitle}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AttachmentOthersView', params: {attachmentOthersId: attachmentOthers.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AttachmentOthersEdit', params: {attachmentOthersId: attachmentOthers.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(attachmentOthers)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-attachmentOthers-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.delete.question', {'id': removeId})">Are you sure you want to delete this Attachment Others?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-attachmentOthers" v-text="$t('entity.action.delete')" v-on:click="removeAttachmentOthers()">Delete</button>
            </div>
        </b-modal>
        <div v-show="attachmentOthers && attachmentOthers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./attachment-others.component.ts">
</script>
