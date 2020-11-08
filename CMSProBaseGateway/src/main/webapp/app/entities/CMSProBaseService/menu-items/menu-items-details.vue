<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="menuItems">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.detail.title')">MenuItems</span> {{menuItems.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{menuItems.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{menuItems.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{menuItems.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.userRoles')">User Roles</span>
                    </dt>
                    <dd>
                        <div v-if="menuItems.userRolesId">
                            <router-link :to="{name: 'UserRolesView', params: {userRolesId: menuItems.userRolesId}}">{{menuItems.userRolesDescription}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="menuItems.id" :to="{name: 'MenuItemsEdit', params: {menuItemsId: menuItems.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./menu-items-details.component.ts">
</script>
