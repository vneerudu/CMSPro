<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.home.createOrEditLabel')">Create or edit a RFIStatuses</h2>
                <div>
                    <div class="form-group" v-if="rFIStatuses.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rFIStatuses.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.code')" for="rfi-statuses-code">Code</label>
                        <input type="text" class="form-control" name="code" id="rfi-statuses-code"
                            :class="{'valid': !$v.rFIStatuses.code.$invalid, 'invalid': $v.rFIStatuses.code.$invalid }" v-model="$v.rFIStatuses.code.$model"  required/>
                        <div v-if="$v.rFIStatuses.code.$anyDirty && $v.rFIStatuses.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFIStatuses.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.description')" for="rfi-statuses-description">Description</label>
                        <input type="text" class="form-control" name="description" id="rfi-statuses-description"
                            :class="{'valid': !$v.rFIStatuses.description.$invalid, 'invalid': $v.rFIStatuses.description.$invalid }" v-model="$v.rFIStatuses.description.$model"  required/>
                        <div v-if="$v.rFIStatuses.description.$anyDirty && $v.rFIStatuses.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFIStatuses.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.isActive')" for="rfi-statuses-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="rfi-statuses-isActive"
                            :class="{'valid': !$v.rFIStatuses.isActive.$invalid, 'invalid': $v.rFIStatuses.isActive.$invalid }" v-model="$v.rFIStatuses.isActive.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rFIStatuses.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rfi-statuses-update.component.ts">
</script>
