<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.home.createOrEditLabel')">Create or edit a ProjectTeams</h2>
                <div>
                    <div class="form-group" v-if="projectTeams.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="projectTeams.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.name')" for="project-teams-name">Name</label>
                        <input type="text" class="form-control" name="name" id="project-teams-name"
                            :class="{'valid': !$v.projectTeams.name.$invalid, 'invalid': $v.projectTeams.name.$invalid }" v-model="$v.projectTeams.name.$model"  required/>
                        <div v-if="$v.projectTeams.name.$anyDirty && $v.projectTeams.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectTeams.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.createdBy')" for="project-teams-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="project-teams-createdBy"
                            :class="{'valid': !$v.projectTeams.createdBy.$invalid, 'invalid': $v.projectTeams.createdBy.$invalid }" v-model="$v.projectTeams.createdBy.$model"  required/>
                        <div v-if="$v.projectTeams.createdBy.$anyDirty && $v.projectTeams.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectTeams.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.creationDate')" for="project-teams-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="project-teams-creationDate"
                                    v-model="$v.projectTeams.creationDate.$model"
                                    name="creationDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="project-teams-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.projectTeams.creationDate.$invalid, 'invalid': $v.projectTeams.creationDate.$invalid }"
                            v-model="$v.projectTeams.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.projectTeams.creationDate.$anyDirty && $v.projectTeams.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projectTeams.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.project')" for="project-teams-project">Project</label>
                        <select class="form-control" id="project-teams-project" name="project" v-model="projectTeams.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.projectTeams.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./project-teams-update.component.ts">
</script>
