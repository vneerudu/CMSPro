<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.title')" id="attachments-heading">Attachments</span>
            <router-link :to="{name: 'AttachmentsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-attachments">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.createLabel')">
                    Create a new Attachments
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && attachments && attachments.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.notFound')">No attachments found</span>
        </div>
        <div class="table-responsive" v-if="attachments && attachments.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('folder')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.folder')">Folder</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'folder'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.fileName')">File Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('imageFileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.image')">Image</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'imageFileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('pdfattachmentFileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.pdfattachment')">Pdfattachment</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pdfattachmentFileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sheetNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.sheet')">Sheet</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sheetNumber'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="attachments in attachments"
                    :key="attachments.id">
                    <td>
                        <router-link :to="{name: 'AttachmentsView', params: {attachmentsId: attachments.id}}">{{attachments.id}}</router-link>
                    </td>
                    <td>{{attachments.folder}}</td>
                    <td>{{attachments.fileName}}</td>
                    <td>{{attachments.createdBy}}</td>
                    <td>{{attachments.creationDate}}</td>
                    <td>
                        <div v-if="attachments.imageId">
                            <router-link :to="{name: 'AttachmentImagesView', params: {attachmentImagesId: attachments.imageId}}">{{attachments.imageFileName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="attachments.pdfattachmentId">
                            <router-link :to="{name: 'AttachmentOthersView', params: {attachmentOthersId: attachments.pdfattachmentId}}">{{attachments.pdfattachmentFileName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="attachments.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: attachments.sheetId}}">{{attachments.sheetNumber}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AttachmentsView', params: {attachmentsId: attachments.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AttachmentsEdit', params: {attachmentsId: attachments.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(attachments)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceAttachments.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-attachments-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.delete.question', {'id': removeId})">Are you sure you want to delete this Attachments?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-attachments" v-text="$t('entity.action.delete')" v-on:click="removeAttachments()">Delete</button>
            </div>
        </b-modal>
        <div v-show="attachments && attachments.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./attachments.component.ts">
</script>
