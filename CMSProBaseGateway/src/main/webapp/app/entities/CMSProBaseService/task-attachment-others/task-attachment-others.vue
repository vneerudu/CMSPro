<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.title')" id="task-attachment-others-heading">Task Attachment Others</span>
            <router-link :to="{name: 'TaskAttachmentOthersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-task-attachment-others">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.createLabel')">
                    Create a new Task Attachment Others
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && taskAttachmentOthers && taskAttachmentOthers.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.notFound')">No taskAttachmentOthers found</span>
        </div>
        <div class="table-responsive" v-if="taskAttachmentOthers && taskAttachmentOthers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileName')">File Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fileType')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileType')">File Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('taskTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.task')">Task</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskTitle'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rfiTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.rfi')">Rfi</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rfiTitle'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="taskAttachmentOthers in taskAttachmentOthers"
                    :key="taskAttachmentOthers.id">
                    <td>
                        <router-link :to="{name: 'TaskAttachmentOthersView', params: {taskAttachmentOthersId: taskAttachmentOthers.id}}">{{taskAttachmentOthers.id}}</router-link>
                    </td>
                    <td>{{taskAttachmentOthers.fileName}}</td>
                    <td>{{taskAttachmentOthers.fileType}}</td>
                    <td>
                        <a v-if="taskAttachmentOthers.content" v-on:click="openFile(taskAttachmentOthers.contentContentType, taskAttachmentOthers.content)" v-text="$t('entity.action.open')">open</a>
                        <span v-if="taskAttachmentOthers.content">{{taskAttachmentOthers.contentContentType}}, {{byteSize(taskAttachmentOthers.content)}}</span>
                    </td>
                    <td>{{taskAttachmentOthers.createdBy}}</td>
                    <td>{{taskAttachmentOthers.creationDate}}</td>
                    <td>
                        <div v-if="taskAttachmentOthers.taskId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: taskAttachmentOthers.taskId}}">{{taskAttachmentOthers.taskTitle}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="taskAttachmentOthers.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: taskAttachmentOthers.rfiId}}">{{taskAttachmentOthers.rfiTitle}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TaskAttachmentOthersView', params: {taskAttachmentOthersId: taskAttachmentOthers.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TaskAttachmentOthersEdit', params: {taskAttachmentOthersId: taskAttachmentOthers.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(taskAttachmentOthers)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-taskAttachmentOthers-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.delete.question', {'id': removeId})">Are you sure you want to delete this Task Attachment Others?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-taskAttachmentOthers" v-text="$t('entity.action.delete')" v-on:click="removeTaskAttachmentOthers()">Delete</button>
            </div>
        </b-modal>
        <div v-show="taskAttachmentOthers && taskAttachmentOthers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-attachment-others.component.ts">
</script>
