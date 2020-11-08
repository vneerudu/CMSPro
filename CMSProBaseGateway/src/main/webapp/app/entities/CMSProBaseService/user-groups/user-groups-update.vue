<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.home.createOrEditLabel')">Create or edit a UserGroups</h2>
                <div>
                    <div class="form-group" v-if="userGroups.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="userGroups.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.code')" for="user-groups-code">Code</label>
                        <input type="text" class="form-control" name="code" id="user-groups-code"
                            :class="{'valid': !$v.userGroups.code.$invalid, 'invalid': $v.userGroups.code.$invalid }" v-model="$v.userGroups.code.$model"  required/>
                        <div v-if="$v.userGroups.code.$anyDirty && $v.userGroups.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userGroups.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.description')" for="user-groups-description">Description</label>
                        <input type="text" class="form-control" name="description" id="user-groups-description"
                            :class="{'valid': !$v.userGroups.description.$invalid, 'invalid': $v.userGroups.description.$invalid }" v-model="$v.userGroups.description.$model"  required/>
                        <div v-if="$v.userGroups.description.$anyDirty && $v.userGroups.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.userGroups.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.account')" for="user-groups-account">Account</label>
                        <select class="form-control" id="user-groups-account" name="account" v-model="userGroups.accountId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="accountsOption.id" v-for="accountsOption in accounts" :key="accountsOption.id">{{accountsOption.accountNumber}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceUserGroups.users')" for="user-groups-users">Users</label>
                        <select class="form-control" id="user-groups-users" name="users" v-model="userGroups.usersId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="usersOption.id" v-for="usersOption in users" :key="usersOption.id">{{usersOption.fullName}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.userGroups.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./user-groups-update.component.ts">
</script>
