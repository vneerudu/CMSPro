<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.home.createOrEditLabel')">Create or edit a RootCauses</h2>
                <div>
                    <div class="form-group" v-if="rootCauses.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rootCauses.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.code')" for="root-causes-code">Code</label>
                        <input type="text" class="form-control" name="code" id="root-causes-code"
                            :class="{'valid': !$v.rootCauses.code.$invalid, 'invalid': $v.rootCauses.code.$invalid }" v-model="$v.rootCauses.code.$model"  required/>
                        <div v-if="$v.rootCauses.code.$anyDirty && $v.rootCauses.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rootCauses.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.description')" for="root-causes-description">Description</label>
                        <input type="text" class="form-control" name="description" id="root-causes-description"
                            :class="{'valid': !$v.rootCauses.description.$invalid, 'invalid': $v.rootCauses.description.$invalid }" v-model="$v.rootCauses.description.$model"  required/>
                        <div v-if="$v.rootCauses.description.$anyDirty && $v.rootCauses.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rootCauses.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.isActive')" for="root-causes-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="root-causes-isActive"
                            :class="{'valid': !$v.rootCauses.isActive.$invalid, 'invalid': $v.rootCauses.isActive.$invalid }" v-model="$v.rootCauses.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.groups')" for="root-causes-groups">Groups</label>
                        <select class="form-control" id="root-causes-groups" name="groups" v-model="rootCauses.groupsId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rootCauseGroupsOption.id" v-for="rootCauseGroupsOption in rootCauseGroups" :key="rootCauseGroupsOption.id">{{rootCauseGroupsOption.code}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rootCauses.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./root-causes-update.component.ts">
</script>
