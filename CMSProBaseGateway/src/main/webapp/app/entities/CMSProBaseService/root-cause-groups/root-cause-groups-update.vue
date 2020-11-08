<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.home.createOrEditLabel')">Create or edit a RootCauseGroups</h2>
                <div>
                    <div class="form-group" v-if="rootCauseGroups.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rootCauseGroups.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.code')" for="root-cause-groups-code">Code</label>
                        <input type="text" class="form-control" name="code" id="root-cause-groups-code"
                            :class="{'valid': !$v.rootCauseGroups.code.$invalid, 'invalid': $v.rootCauseGroups.code.$invalid }" v-model="$v.rootCauseGroups.code.$model"  required/>
                        <div v-if="$v.rootCauseGroups.code.$anyDirty && $v.rootCauseGroups.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rootCauseGroups.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.description')" for="root-cause-groups-description">Description</label>
                        <input type="text" class="form-control" name="description" id="root-cause-groups-description"
                            :class="{'valid': !$v.rootCauseGroups.description.$invalid, 'invalid': $v.rootCauseGroups.description.$invalid }" v-model="$v.rootCauseGroups.description.$model"  required/>
                        <div v-if="$v.rootCauseGroups.description.$anyDirty && $v.rootCauseGroups.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rootCauseGroups.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.isActive')" for="root-cause-groups-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="root-cause-groups-isActive"
                            :class="{'valid': !$v.rootCauseGroups.isActive.$invalid, 'invalid': $v.rootCauseGroups.isActive.$invalid }" v-model="$v.rootCauseGroups.isActive.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rootCauseGroups.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./root-cause-groups-update.component.ts">
</script>
