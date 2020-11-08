<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceStates.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStates.home.createOrEditLabel')">Create or edit a States</h2>
                <div>
                    <div class="form-group" v-if="states.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="states.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStates.code')" for="states-code">Code</label>
                        <input type="text" class="form-control" name="code" id="states-code"
                            :class="{'valid': !$v.states.code.$invalid, 'invalid': $v.states.code.$invalid }" v-model="$v.states.code.$model"  required/>
                        <div v-if="$v.states.code.$anyDirty && $v.states.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.states.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStates.description')" for="states-description">Description</label>
                        <input type="text" class="form-control" name="description" id="states-description"
                            :class="{'valid': !$v.states.description.$invalid, 'invalid': $v.states.description.$invalid }" v-model="$v.states.description.$model"  required/>
                        <div v-if="$v.states.description.$anyDirty && $v.states.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.states.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.states.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./states-update.component.ts">
</script>
