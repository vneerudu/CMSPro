<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.home.createOrEditLabel')">Create or edit a TaskTypes</h2>
                <div>
                    <div class="form-group" v-if="taskTypes.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskTypes.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.code')" for="task-types-code">Code</label>
                        <input type="text" class="form-control" name="code" id="task-types-code"
                            :class="{'valid': !$v.taskTypes.code.$invalid, 'invalid': $v.taskTypes.code.$invalid }" v-model="$v.taskTypes.code.$model"  required/>
                        <div v-if="$v.taskTypes.code.$anyDirty && $v.taskTypes.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskTypes.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.description')" for="task-types-description">Description</label>
                        <input type="text" class="form-control" name="description" id="task-types-description"
                            :class="{'valid': !$v.taskTypes.description.$invalid, 'invalid': $v.taskTypes.description.$invalid }" v-model="$v.taskTypes.description.$model"  required/>
                        <div v-if="$v.taskTypes.description.$anyDirty && $v.taskTypes.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskTypes.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.isActive')" for="task-types-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="task-types-isActive"
                            :class="{'valid': !$v.taskTypes.isActive.$invalid, 'invalid': $v.taskTypes.isActive.$invalid }" v-model="$v.taskTypes.isActive.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskTypes.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-types-update.component.ts">
</script>
