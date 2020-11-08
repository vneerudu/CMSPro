<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userRoles">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.detail.title')">UserRoles</span> {{userRoles.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{userRoles.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{userRoles.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{userRoles.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.userGroup')">User Group</span>
                    </dt>
                    <dd>
                        <div v-if="userRoles.userGroupId">
                            <router-link :to="{name: 'UserGroupsView', params: {userGroupsId: userRoles.userGroupId}}">{{userRoles.userGroupDescription}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userRoles.id" :to="{name: 'UserRolesEdit', params: {userRolesId: userRoles.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-roles-details.component.ts">
</script>
