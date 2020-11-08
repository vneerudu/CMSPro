<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="users">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.detail.title')">Users</span> {{users.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.firstName')">First Name</span>
                    </dt>
                    <dd>
                        <span>{{users.firstName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.lastName')">Last Name</span>
                    </dt>
                    <dd>
                        <span>{{users.lastName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.fullName')">Full Name</span>
                    </dt>
                    <dd>
                        <span>{{users.fullName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.prefix')">Prefix</span>
                    </dt>
                    <dd>
                        <span>{{users.prefix}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.emailAddress')">Email Address</span>
                    </dt>
                    <dd>
                        <span>{{users.emailAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.phoneNumber')">Phone Number</span>
                    </dt>
                    <dd>
                        <span>{{users.phoneNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{users.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.company')">Company</span>
                    </dt>
                    <dd>
                        <span>{{users.company}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.trackLocation')">Track Location</span>
                    </dt>
                    <dd>
                        <span>{{users.trackLocation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.account')">Account</span>
                    </dt>
                    <dd>
                        <div v-if="users.accountId">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: users.accountId}}">{{users.accountAccountNumber}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="users.teamId">
                            <router-link :to="{name: 'ProjectTeamsView', params: {projectTeamsId: users.teamId}}">{{users.teamName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskAssigned')">Task Assigned</span>
                    </dt>
                    <dd>
                        <div v-if="users.taskAssignedId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: users.taskAssignedId}}">{{users.taskAssignedTitle}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUsers.taskToMonitor')">Task To Monitor</span>
                    </dt>
                    <dd>
                        <div v-if="users.taskToMonitorId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: users.taskToMonitorId}}">{{users.taskToMonitorTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="users.id" :to="{name: 'UsersEdit', params: {usersId: users.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./users-details.component.ts">
</script>
