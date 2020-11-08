<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceLists.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.home.createOrEditLabel')">Create or edit a Lists</h2>
                <div>
                    <div class="form-group" v-if="lists.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="lists.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.code')" for="lists-code">Code</label>
                        <input type="text" class="form-control" name="code" id="lists-code"
                            :class="{'valid': !$v.lists.code.$invalid, 'invalid': $v.lists.code.$invalid }" v-model="$v.lists.code.$model"  required/>
                        <div v-if="$v.lists.code.$anyDirty && $v.lists.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.lists.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.description')" for="lists-description">Description</label>
                        <input type="text" class="form-control" name="description" id="lists-description"
                            :class="{'valid': !$v.lists.description.$invalid, 'invalid': $v.lists.description.$invalid }" v-model="$v.lists.description.$model"  required/>
                        <div v-if="$v.lists.description.$anyDirty && $v.lists.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.lists.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.isActive')" for="lists-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="lists-isActive"
                            :class="{'valid': !$v.lists.isActive.$invalid, 'invalid': $v.lists.isActive.$invalid }" v-model="$v.lists.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.project')" for="lists-project">Project</label>
                        <select class="form-control" id="lists-project" name="project" v-model="lists.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.lists.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./lists-update.component.ts">
</script>
