<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.home.createOrEditLabel')">Create or edit a UserRoles</h2>
                <div>
                    <div class="form-group" v-if="userRoles.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="userRoles.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.code')" for="user-roles-code">Code</label>
                        <input type="text" class="form-control" name="code" id="user-roles-code"
                            :class="{'valid': !$v.userRoles.code.$invalid, 'invalid': $v.userRoles.code.$invalid }" v-model="$v.userRoles.code.$model"  required/>
                        <div v-if="$v.userRoles.code.$anyDirty && $v.userRoles.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userRoles.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.description')" for="user-roles-description">Description</label>
                        <input type="text" class="form-control" name="description" id="user-roles-description"
                            :class="{'valid': !$v.userRoles.description.$invalid, 'invalid': $v.userRoles.description.$invalid }" v-model="$v.userRoles.description.$model"  required/>
                        <div v-if="$v.userRoles.description.$anyDirty && $v.userRoles.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userRoles.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.isActive')" for="user-roles-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="user-roles-isActive"
                            :class="{'valid': !$v.userRoles.isActive.$invalid, 'invalid': $v.userRoles.isActive.$invalid }" v-model="$v.userRoles.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserRoles.userGroup')" for="user-roles-userGroup">User Group</label>
                        <select class="form-control" id="user-roles-userGroup" name="userGroup" v-model="userRoles.userGroupId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="userGroupsOption.id" v-for="userGroupsOption in userGroups" :key="userGroupsOption.id">{{userGroupsOption.description}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.userRoles.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./user-roles-update.component.ts">
</script>
