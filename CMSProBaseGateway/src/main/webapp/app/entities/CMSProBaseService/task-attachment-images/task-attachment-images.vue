<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.title')" id="task-attachment-images-heading">Task Attachment Images</span>
            <router-link :to="{name: 'TaskAttachmentImagesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-task-attachment-images">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.createLabel')">
                    Create a new Task Attachment Images
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && taskAttachmentImages && taskAttachmentImages.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.notFound')">No taskAttachmentImages found</span>
        </div>
        <div class="table-responsive" v-if="taskAttachmentImages && taskAttachmentImages.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileName')">File Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileType')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileType')">File Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('taskTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.task')">Task</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskTitle'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rfiTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.rfi')">Rfi</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rfiTitle'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="taskAttachmentImages in taskAttachmentImages"
                    :key="taskAttachmentImages.id">
                    <td>
                        <router-link :to="{name: 'TaskAttachmentImagesView', params: {taskAttachmentImagesId: taskAttachmentImages.id}}">{{taskAttachmentImages.id}}</router-link>
                    </td>
                    <td>{{taskAttachmentImages.fileName}}</td>
                    <td>{{taskAttachmentImages.fileType}}</td>
                    <td>
                        <a v-if="taskAttachmentImages.content" v-on:click="openFile(taskAttachmentImages.contentContentType, taskAttachmentImages.content)" v-text="$t('entity.action.open')">open</a>
                        <span v-if="taskAttachmentImages.content">{{taskAttachmentImages.contentContentType}}, {{byteSize(taskAttachmentImages.content)}}</span>
                    </td>
                    <td>{{taskAttachmentImages.createdBy}}</td>
                    <td>{{taskAttachmentImages.creationDate}}</td>
                    <td>
                        <div v-if="taskAttachmentImages.taskId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: taskAttachmentImages.taskId}}">{{taskAttachmentImages.taskTitle}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="taskAttachmentImages.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: taskAttachmentImages.rfiId}}">{{taskAttachmentImages.rfiTitle}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TaskAttachmentImagesView', params: {taskAttachmentImagesId: taskAttachmentImages.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TaskAttachmentImagesEdit', params: {taskAttachmentImagesId: taskAttachmentImages.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(taskAttachmentImages)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-taskAttachmentImages-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.delete.question', {'id': removeId})">Are you sure you want to delete this Task Attachment Images?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-taskAttachmentImages" v-text="$t('entity.action.delete')" v-on:click="removeTaskAttachmentImages()">Delete</button>
            </div>
        </b-modal>
        <div v-show="taskAttachmentImages && taskAttachmentImages.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-attachment-images.component.ts">
</script>
