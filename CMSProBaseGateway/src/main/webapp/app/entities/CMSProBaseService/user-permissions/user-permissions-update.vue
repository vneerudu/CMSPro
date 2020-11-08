<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.home.createOrEditLabel')">Create or edit a UserPermissions</h2>
                <div>
                    <div class="form-group" v-if="userPermissions.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="userPermissions.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.code')" for="user-permissions-code">Code</label>
                        <input type="text" class="form-control" name="code" id="user-permissions-code"
                            :class="{'valid': !$v.userPermissions.code.$invalid, 'invalid': $v.userPermissions.code.$invalid }" v-model="$v.userPermissions.code.$model"  required/>
                        <div v-if="$v.userPermissions.code.$anyDirty && $v.userPermissions.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userPermissions.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.description')" for="user-permissions-description">Description</label>
                        <input type="text" class="form-control" name="description" id="user-permissions-description"
                            :class="{'valid': !$v.userPermissions.description.$invalid, 'invalid': $v.userPermissions.description.$invalid }" v-model="$v.userPermissions.description.$model"  required/>
                        <div v-if="$v.userPermissions.description.$anyDirty && $v.userPermissions.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userPermissions.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.isActive')" for="user-permissions-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="user-permissions-isActive"
                            :class="{'valid': !$v.userPermissions.isActive.$invalid, 'invalid': $v.userPermissions.isActive.$invalid }" v-model="$v.userPermissions.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserPermissions.userRoles')" for="user-permissions-userRoles">User Roles</label>
                        <select class="form-control" id="user-permissions-userRoles" name="userRoles" v-model="userPermissions.userRolesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userRolesOption.id" v-for="userRolesOption in userRoles" :key="userRolesOption.id">{{userRolesOption.description}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.userPermissions.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./user-permissions-update.component.ts">
</script>
