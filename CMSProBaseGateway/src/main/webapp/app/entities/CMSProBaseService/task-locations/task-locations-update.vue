<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.home.createOrEditLabel')">Create or edit a TaskLocations</h2>
                <div>
                    <div class="form-group" v-if="taskLocations.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskLocations.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.code')" for="task-locations-code">Code</label>
                        <input type="text" class="form-control" name="code" id="task-locations-code"
                            :class="{'valid': !$v.taskLocations.code.$invalid, 'invalid': $v.taskLocations.code.$invalid }" v-model="$v.taskLocations.code.$model"  required/>
                        <div v-if="$v.taskLocations.code.$anyDirty && $v.taskLocations.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskLocations.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.description')" for="task-locations-description">Description</label>
                        <input type="text" class="form-control" name="description" id="task-locations-description"
                            :class="{'valid': !$v.taskLocations.description.$invalid, 'invalid': $v.taskLocations.description.$invalid }" v-model="$v.taskLocations.description.$model"  required/>
                        <div v-if="$v.taskLocations.description.$anyDirty && $v.taskLocations.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskLocations.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.isActive')" for="task-locations-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="task-locations-isActive"
                            :class="{'valid': !$v.taskLocations.isActive.$invalid, 'invalid': $v.taskLocations.isActive.$invalid }" v-model="$v.taskLocations.isActive.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskLocations.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-locations-update.component.ts">
</script>
