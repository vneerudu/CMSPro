<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.title')" id="users-heading">Users</span>
            <router-link :to="{name: 'UsersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-users">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.createLabel')">
                    Create a new Users
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && users && users.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.notFound')">No users found</span>
        </div>
        <div class="table-responsive" v-if="users && users.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('firstName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.firstName')">First Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lastName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.lastName')">Last Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fullName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.fullName')">Full Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('prefix')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.prefix')">Prefix</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prefix'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('emailAddress')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.emailAddress')">Email Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'emailAddress'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phoneNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.phoneNumber')">Phone Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.title')">Title</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('company')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.company')">Company</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('trackLocation')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.trackLocation')">Track Location</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'trackLocation'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('accountAccountNumber')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.account')">Account</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accountAccountNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('teamName')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.team')">Team</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'teamName'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('taskAssignedTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskAssigned')">Task Assigned</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskAssignedTitle'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('taskToMonitorTitle')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskToMonitor')">Task To Monitor</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskToMonitorTitle'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="users in users"
                    :key="users.id">
                    <td>
                        <router-link :to="{name: 'UsersView', params: {usersId: users.id}}">{{users.id}}</router-link>
                    </td>
                    <td>{{users.firstName}}</td>
                    <td>{{users.lastName}}</td>
                    <td>{{users.fullName}}</td>
                    <td>{{users.prefix}}</td>
                    <td>{{users.emailAddress}}</td>
                    <td>{{users.phoneNumber}}</td>
                    <td>{{users.title}}</td>
                    <td>{{users.company}}</td>
                    <td>{{users.trackLocation}}</td>
                    <td>
                        <div v-if="users.accountId">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: users.accountId}}">{{users.accountAccountNumber}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="users.teamId">
                            <router-link :to="{name: 'ProjectTeamsView', params: {projectTeamsId: users.teamId}}">{{users.teamName}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="users.taskAssignedId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: users.taskAssignedId}}">{{users.taskAssignedTitle}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="users.taskToMonitorId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: users.taskToMonitorId}}">{{users.taskToMonitorTitle}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'UsersView', params: {usersId: users.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UsersEdit', params: {usersId: users.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(users)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceUsers.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-users-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.delete.question', {'id': removeId})">Are you sure you want to delete this Users?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-users" v-text="$t('entity.action.delete')" v-on:click="removeUsers()">Delete</button>
            </div>
        </b-modal>
        <div v-show="users && users.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./users.component.ts">
</script>
