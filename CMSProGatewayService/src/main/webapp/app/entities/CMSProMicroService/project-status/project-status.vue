<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.title')" id="project-status-heading">Project Statuses</span>
            <router-link :to="{name: 'ProjectStatusCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-project-status">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.createLabel')">
                    Create a new Project Status
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
                            v-bind:placeholder="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && projectStatuses && projectStatuses.length === 0">
            <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.notFound')">No projectStatuses found</span>
        </div>
        <div class="table-responsive" v-if="projectStatuses && projectStatuses.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isActive')"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.isActive')">Is Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="projectStatus in projectStatuses"
                    :key="projectStatus.id">
                    <td>
                        <router-link :to="{name: 'ProjectStatusView', params: {projectStatusId: projectStatus.id}}">{{projectStatus.id}}</router-link>
                    </td>
                    <td>{{projectStatus.code}}</td>
                    <td>{{projectStatus.description}}</td>
                    <td>{{projectStatus.isActive}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProjectStatusView', params: {projectStatusId: projectStatus.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProjectStatusEdit', params: {projectStatusId: projectStatus.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(projectStatus)"
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
            <span slot="modal-title"><span id="cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-projectStatus-heading" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.delete.question', {'id': removeId})">Are you sure you want to delete this Project Status?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-projectStatus" v-text="$t('entity.action.delete')" v-on:click="removeProjectStatus()">Delete</button>
            </div>
        </b-modal>
        <div v-show="projectStatuses && projectStatuses.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./project-status.component.ts">
</script>
