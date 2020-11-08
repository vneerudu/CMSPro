<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.title')" id="tasks-heading">Tasks</span>
            <router-link :to="{name: 'TasksCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tasks">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.createLabel')">
                    Create a new Tasks
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && tasks && tasks.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.notFound')">No tasks found</span>
        </div>
        <div class="table-responsive" v-if="tasks && tasks.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('startDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.startDate')">Start Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('dueDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.dueDate')">Due Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dueDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('costImpact')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpact')">Cost Impact</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'costImpact'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('costImpactComment')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpactComment')">Cost Impact Comment</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'costImpactComment'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('scheduleImpact')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpact')">Schedule Impact</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'scheduleImpact'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('scheduleImpactComment')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpactComment')">Schedule Impact Comment</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'scheduleImpactComment'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('typeDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.type')">Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('statusDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statusDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('locationDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.location')">Location</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('stampTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.stamp')">Stamp</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stampTitle'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('listDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.list')">List</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'listDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sheetNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.sheet')">Sheet</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sheetNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('rootCausesDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.rootCauses')">Root Causes</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rootCausesDescription'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('projectName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.project')">Project</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'projectName'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="tasks in tasks"
                    :key="tasks.id">
                    <td>
                        <router-link :to="{name: 'TasksView', params: {tasksId: tasks.id}}">{{tasks.id}}</router-link>
                    </td>
                    <td>{{tasks.title}}</td>
                    <td>{{tasks.startDate}}</td>
                    <td>{{tasks.dueDate}}</td>
                    <td>{{tasks.description}}</td>
                    <td>{{tasks.costImpact}}</td>
                    <td>{{tasks.costImpactComment}}</td>
                    <td>{{tasks.scheduleImpact}}</td>
                    <td>{{tasks.scheduleImpactComment}}</td>
                    <td>{{tasks.createdBy}}</td>
                    <td>{{tasks.creationDate}}</td>
                    <td>
                        <div v-if="tasks.typeId">
                            <router-link :to="{name: 'TaskTypesView', params: {taskTypesId: tasks.typeId}}">{{tasks.typeDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.statusId">
                            <router-link :to="{name: 'TaskStatusesView', params: {taskStatusesId: tasks.statusId}}">{{tasks.statusDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.locationId">
                            <router-link :to="{name: 'TaskLocationsView', params: {taskLocationsId: tasks.locationId}}">{{tasks.locationDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.stampId">
                            <router-link :to="{name: 'StampsView', params: {stampsId: tasks.stampId}}">{{tasks.stampTitle}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.listId">
                            <router-link :to="{name: 'ListsView', params: {listsId: tasks.listId}}">{{tasks.listDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: tasks.sheetId}}">{{tasks.sheetNumber}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.rootCausesId">
                            <router-link :to="{name: 'RootCausesView', params: {rootCausesId: tasks.rootCausesId}}">{{tasks.rootCausesDescription}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="tasks.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: tasks.projectId}}">{{tasks.projectName}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TasksView', params: {tasksId: tasks.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TasksEdit', params: {tasksId: tasks.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(tasks)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceTasks.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-tasks-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.delete.question', {'id': removeId})">Are you sure you want to delete this Tasks?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-tasks" v-text="$t('entity.action.delete')" v-on:click="removeTasks()">Delete</button>
            </div>
        </b-modal>
        <div v-show="tasks && tasks.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tasks.component.ts">
</script>
