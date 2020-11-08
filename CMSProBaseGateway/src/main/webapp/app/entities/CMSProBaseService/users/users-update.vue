<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.home.createOrEditLabel')">Create or edit a Users</h2>
                <div>
                    <div class="form-group" v-if="users.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="users.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.firstName')" for="users-firstName">First Name</label>
                        <input type="text" class="form-control" name="firstName" id="users-firstName"
                            :class="{'valid': !$v.users.firstName.$invalid, 'invalid': $v.users.firstName.$invalid }" v-model="$v.users.firstName.$model"  required/>
                        <div v-if="$v.users.firstName.$anyDirty && $v.users.firstName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.users.firstName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.lastName')" for="users-lastName">Last Name</label>
                        <input type="text" class="form-control" name="lastName" id="users-lastName"
                            :class="{'valid': !$v.users.lastName.$invalid, 'invalid': $v.users.lastName.$invalid }" v-model="$v.users.lastName.$model"  required/>
                        <div v-if="$v.users.lastName.$anyDirty && $v.users.lastName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.users.lastName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.fullName')" for="users-fullName">Full Name</label>
                        <input type="text" class="form-control" name="fullName" id="users-fullName"
                            :class="{'valid': !$v.users.fullName.$invalid, 'invalid': $v.users.fullName.$invalid }" v-model="$v.users.fullName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.prefix')" for="users-prefix">Prefix</label>
                        <input type="text" class="form-control" name="prefix" id="users-prefix"
                            :class="{'valid': !$v.users.prefix.$invalid, 'invalid': $v.users.prefix.$invalid }" v-model="$v.users.prefix.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.emailAddress')" for="users-emailAddress">Email Address</label>
                        <input type="text" class="form-control" name="emailAddress" id="users-emailAddress"
                            :class="{'valid': !$v.users.emailAddress.$invalid, 'invalid': $v.users.emailAddress.$invalid }" v-model="$v.users.emailAddress.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.phoneNumber')" for="users-phoneNumber">Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" id="users-phoneNumber"
                            :class="{'valid': !$v.users.phoneNumber.$invalid, 'invalid': $v.users.phoneNumber.$invalid }" v-model="$v.users.phoneNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.title')" for="users-title">Title</label>
                        <input type="text" class="form-control" name="title" id="users-title"
                            :class="{'valid': !$v.users.title.$invalid, 'invalid': $v.users.title.$invalid }" v-model="$v.users.title.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.company')" for="users-company">Company</label>
                        <input type="text" class="form-control" name="company" id="users-company"
                            :class="{'valid': !$v.users.company.$invalid, 'invalid': $v.users.company.$invalid }" v-model="$v.users.company.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.trackLocation')" for="users-trackLocation">Track Location</label>
                        <input type="checkbox" class="form-check" name="trackLocation" id="users-trackLocation"
                            :class="{'valid': !$v.users.trackLocation.$invalid, 'invalid': $v.users.trackLocation.$invalid }" v-model="$v.users.trackLocation.$model"  required/>
                        <div v-if="$v.users.trackLocation.$anyDirty && $v.users.trackLocation.$invalid">
                            <small class="form-text text-danger" v-if="!$v.users.trackLocation.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.account')" for="users-account">Account</label>
                        <select class="form-control" id="users-account" name="account" v-model="users.accountId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="accountsOption.id" v-for="accountsOption in accounts" :key="accountsOption.id">{{accountsOption.accountNumber}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.team')" for="users-team">Team</label>
                        <select class="form-control" id="users-team" name="team" v-model="users.teamId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectTeamsOption.id" v-for="projectTeamsOption in projectTeams" :key="projectTeamsOption.id">{{projectTeamsOption.name}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskAssigned')" for="users-taskAssigned">Task Assigned</label>
                        <select class="form-control" id="users-taskAssigned" name="taskAssigned" v-model="users.taskAssignedId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="tasksOption.id" v-for="tasksOption in tasks" :key="tasksOption.id">{{tasksOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskToMonitor')" for="users-taskToMonitor">Task To Monitor</label>
                        <select class="form-control" id="users-taskToMonitor" name="taskToMonitor" v-model="users.taskToMonitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="tasksOption.id" v-for="tasksOption in tasks" :key="tasksOption.id">{{tasksOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.users.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./users-update.component.ts">
</script>
