<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userGroups">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.detail.title')">UserGroups</span> {{userGroups.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{userGroups.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{userGroups.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.account')">Account</span>
                    </dt>
                    <dd>
                        <div v-if="userGroups.accountId">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: userGroups.accountId}}">{{userGroups.accountAccountNumber}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.users')">Users</span>
                    </dt>
                    <dd>
                        <div v-if="userGroups.usersId">
                            <router-link :to="{name: 'UsersView', params: {usersId: userGroups.usersId}}">{{userGroups.usersFullName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userGroups.id" :to="{name: 'UserGroupsEdit', params: {userGroupsId: userGroups.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-groups-details.component.ts">
</script>
