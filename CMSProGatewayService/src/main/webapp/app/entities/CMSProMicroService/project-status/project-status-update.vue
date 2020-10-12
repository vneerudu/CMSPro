<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.createOrEditLabel" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.home.createOrEditLabel')">Create or edit a ProjectStatus</h2>
                <div>
                    <div class="form-group" v-if="projectStatus.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="projectStatus.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.code')" for="project-status-code">Code</label>
                        <input type="text" class="form-control" name="code" id="project-status-code"
                            :class="{'valid': !$v.projectStatus.code.$invalid, 'invalid': $v.projectStatus.code.$invalid }" v-model="$v.projectStatus.code.$model"  required/>
                        <div v-if="$v.projectStatus.code.$anyDirty && $v.projectStatus.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatus.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.description')" for="project-status-description">Description</label>
                        <input type="text" class="form-control" name="description" id="project-status-description"
                            :class="{'valid': !$v.projectStatus.description.$invalid, 'invalid': $v.projectStatus.description.$invalid }" v-model="$v.projectStatus.description.$model"  required/>
                        <div v-if="$v.projectStatus.description.$anyDirty && $v.projectStatus.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatus.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.isActive')" for="project-status-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="project-status-isActive"
                            :class="{'valid': !$v.projectStatus.isActive.$invalid, 'invalid': $v.projectStatus.isActive.$invalid }" v-model="$v.projectStatus.isActive.$model"  required/>
                        <div v-if="$v.projectStatus.isActive.$anyDirty && $v.projectStatus.isActive.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectStatus.isActive.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.projectStatus.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./project-status-update.component.ts">
</script>
