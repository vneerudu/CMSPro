<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.home.createOrEditLabel" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.home.createOrEditLabel')">Create or edit a ProjectStatuses</h2>
                <div>
                    <div class="form-group" v-if="projectStatuses.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="projectStatuses.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.status_id')" for="project-statuses-status_id">Status Id</label>
                        <input type="number" class="form-control" name="status_id" id="project-statuses-status_id"
                            :class="{'valid': !$v.projectStatuses.status_id.$invalid, 'invalid': $v.projectStatuses.status_id.$invalid }" v-model.number="$v.projectStatuses.status_id.$model"  required/>
                        <div v-if="$v.projectStatuses.status_id.$anyDirty && $v.projectStatuses.status_id.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatuses.status_id.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.projectStatuses.status_id.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.status_code')" for="project-statuses-status_code">Status Code</label>
                        <input type="text" class="form-control" name="status_code" id="project-statuses-status_code"
                            :class="{'valid': !$v.projectStatuses.status_code.$invalid, 'invalid': $v.projectStatuses.status_code.$invalid }" v-model="$v.projectStatuses.status_code.$model"  required/>
                        <div v-if="$v.projectStatuses.status_code.$anyDirty && $v.projectStatuses.status_code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatuses.status_code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.description')" for="project-statuses-description">Description</label>
                        <input type="text" class="form-control" name="description" id="project-statuses-description"
                            :class="{'valid': !$v.projectStatuses.description.$invalid, 'invalid': $v.projectStatuses.description.$invalid }" v-model="$v.projectStatuses.description.$model"  required/>
                        <div v-if="$v.projectStatuses.description.$anyDirty && $v.projectStatuses.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatuses.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.is_active')" for="project-statuses-is_active">Is Active</label>
                        <input type="checkbox" class="form-check" name="is_active" id="project-statuses-is_active"
                            :class="{'valid': !$v.projectStatuses.is_active.$invalid, 'invalid': $v.projectStatuses.is_active.$invalid }" v-model="$v.projectStatuses.is_active.$model"  required/>
                        <div v-if="$v.projectStatuses.is_active.$anyDirty && $v.projectStatuses.is_active.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatuses.is_active.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.projectStatuses.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./project-statuses-update.component.ts">
</script>
