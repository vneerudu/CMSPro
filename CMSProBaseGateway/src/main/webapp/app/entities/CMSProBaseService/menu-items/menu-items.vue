<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.title')" id="menu-items-heading">Menu Items</span>
            <router-link :to="{name: 'MenuItemsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-menu-items">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.createLabel')">
                    Create a new Menu Items
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
                            v-bind:placeholder="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && menuItems && menuItems.length === 0">
            <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.notFound')">No menuItems found</span>
        </div>
        <div class="table-responsive" v-if="menuItems && menuItems.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.code')">Code</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('isActive')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.isActive')">Is Active</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isActive'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('userRolesDescription')"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.userRoles')">User Roles</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userRolesDescription'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="menuItems in menuItems"
                    :key="menuItems.id">
                    <td>
                        <router-link :to="{name: 'MenuItemsView', params: {menuItemsId: menuItems.id}}">{{menuItems.id}}</router-link>
                    </td>
                    <td>{{menuItems.code}}</td>
                    <td>{{menuItems.description}}</td>
                    <td>{{menuItems.isActive}}</td>
                    <td>
                        <div v-if="menuItems.userRolesId">
                            <router-link :to="{name: 'UserRolesView', params: {userRolesId: menuItems.userRolesId}}">{{menuItems.userRolesDescription}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MenuItemsView', params: {menuItemsId: menuItems.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MenuItemsEdit', params: {menuItemsId: menuItems.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(menuItems)"
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
            <span slot="modal-title"><span id="cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-menuItems-heading" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.delete.question', {'id': removeId})">Are you sure you want to delete this Menu Items?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-menuItems" v-text="$t('entity.action.delete')" v-on:click="removeMenuItems()">Delete</button>
            </div>
        </b-modal>
        <div v-show="menuItems && menuItems.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./menu-items.component.ts">
</script>
