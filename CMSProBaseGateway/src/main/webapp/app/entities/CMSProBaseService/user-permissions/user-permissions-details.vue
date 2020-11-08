<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userPermissions">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.detail.title')">UserPermissions</span> {{userPermissions.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{userPermissions.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{userPermissions.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{userPermissions.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.userRoles')">User Roles</span>
                    </dt>
                    <dd>
                        <div v-if="userPermissions.userRolesId">
                            <router-link :to="{name: 'UserRolesView', params: {userRolesId: userPermissions.userRolesId}}">{{userPermissions.userRolesDescription}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userPermissions.id" :to="{name: 'UserPermissionsEdit', params: {userPermissionsId: userPermissions.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-permissions-details.component.ts">
</script>
