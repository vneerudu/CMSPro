<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.title')" id="projects-heading">Projects</span>
            <router-link :to="{name: 'ProjectsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-projects">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.createLabel')">
                    Create a new Projects
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && projects && projects.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.notFound')">No projects found</span>
        </div>
        <div class="table-responsive" v-if="projects && projects.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('startDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.startDate')">Start Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('finishDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.finishDate')">Finish Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'finishDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lastUpdate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.lastUpdate')">Last Update</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createdBy')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.createdBy')">Created By</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('creationDate')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.creationDate')">Creation Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('accountAccountNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.account')">Account</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accountAccountNumber'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="projects in projects"
                    :key="projects.id">
                    <td>
                        <router-link :to="{name: 'ProjectsView', params: {projectsId: projects.id}}">{{projects.id}}</router-link>
                    </td>
                    <td>{{projects.code}}</td>
                    <td>{{projects.name}}</td>
                    <td>{{projects.startDate}}</td>
                    <td>{{projects.finishDate}}</td>
                    <td>{{projects.lastUpdate}}</td>
                    <td>{{projects.createdBy}}</td>
                    <td>{{projects.creationDate}}</td>
                    <td>
                        <div v-if="projects.accountId">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: projects.accountId}}">{{projects.accountAccountNumber}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: projects.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProjectsEdit', params: {projectsId: projects.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(projects)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceProjects.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-projects-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.delete.question', {'id': removeId})">Are you sure you want to delete this Projects?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-projects" v-text="$t('entity.action.delete')" v-on:click="removeProjects()">Delete</button>
            </div>
        </b-modal>
        <div v-show="projects && projects.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./projects.component.ts">
</script>
