<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.home.createOrEditLabel')">Create or edit a MenuItems</h2>
                <div>
                    <div class="form-group" v-if="menuItems.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="menuItems.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.code')" for="menu-items-code">Code</label>
                        <input type="text" class="form-control" name="code" id="menu-items-code"
                            :class="{'valid': !$v.menuItems.code.$invalid, 'invalid': $v.menuItems.code.$invalid }" v-model="$v.menuItems.code.$model"  required/>
                        <div v-if="$v.menuItems.code.$anyDirty && $v.menuItems.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.menuItems.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.description')" for="menu-items-description">Description</label>
                        <input type="text" class="form-control" name="description" id="menu-items-description"
                            :class="{'valid': !$v.menuItems.description.$invalid, 'invalid': $v.menuItems.description.$invalid }" v-model="$v.menuItems.description.$model"  required/>
                        <div v-if="$v.menuItems.description.$anyDirty && $v.menuItems.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.menuItems.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.isActive')" for="menu-items-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="menu-items-isActive"
                            :class="{'valid': !$v.menuItems.isActive.$invalid, 'invalid': $v.menuItems.isActive.$invalid }" v-model="$v.menuItems.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceMenuItems.userRoles')" for="menu-items-userRoles">User Roles</label>
                        <select class="form-control" id="menu-items-userRoles" name="userRoles" v-model="menuItems.userRolesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userRolesOption.id" v-for="userRolesOption in userRoles" :key="userRolesOption.id">{{userRolesOption.description}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.menuItems.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./menu-items-update.component.ts">
</script>
