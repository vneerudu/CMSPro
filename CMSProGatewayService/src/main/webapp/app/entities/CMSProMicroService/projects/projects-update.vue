<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProGatewayServiceApp.cmsProMicroServiceProjects.home.createOrEditLabel" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.home.createOrEditLabel')">Create or edit a Projects</h2>
                <div>
                    <div class="form-group" v-if="projects.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="projects.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.projectID')" for="projects-projectID">Project ID</label>
                        <input type="number" class="form-control" name="projectID" id="projects-projectID"
                            :class="{'valid': !$v.projects.projectID.$invalid, 'invalid': $v.projects.projectID.$invalid }" v-model.number="$v.projects.projectID.$model"  required/>
                        <div v-if="$v.projects.projectID.$anyDirty && $v.projects.projectID.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.projectID.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.projects.projectID.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.name')" for="projects-name">Name</label>
                        <input type="text" class="form-control" name="name" id="projects-name"
                            :class="{'valid': !$v.projects.name.$invalid, 'invalid': $v.projects.name.$invalid }" v-model="$v.projects.name.$model"  required/>
                        <div v-if="$v.projects.name.$anyDirty && $v.projects.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.department')" for="projects-department">Department</label>
                        <input type="text" class="form-control" name="department" id="projects-department"
                            :class="{'valid': !$v.projects.department.$invalid, 'invalid': $v.projects.department.$invalid }" v-model="$v.projects.department.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.organization')" for="projects-organization">Organization</label>
                        <input type="text" class="form-control" name="organization" id="projects-organization"
                            :class="{'valid': !$v.projects.organization.$invalid, 'invalid': $v.projects.organization.$invalid }" v-model="$v.projects.organization.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.startDate')" for="projects-startDate">Start Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="projects-startDate"
                                    v-model="$v.projects.startDate.$model"
                                    name="startDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="projects-startDate" type="text" class="form-control" name="startDate"  :class="{'valid': !$v.projects.startDate.$invalid, 'invalid': $v.projects.startDate.$invalid }"
                            v-model="$v.projects.startDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.projects.startDate.$anyDirty && $v.projects.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.finishDate')" for="projects-finishDate">Finish Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="projects-finishDate"
                                    v-model="$v.projects.finishDate.$model"
                                    name="finishDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="projects-finishDate" type="text" class="form-control" name="finishDate"  :class="{'valid': !$v.projects.finishDate.$invalid, 'invalid': $v.projects.finishDate.$invalid }"
                            v-model="$v.projects.finishDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.projectStatusRel')" for="projects-projectStatusRel">Project Status Rel</label>
                        <select class="form-control" id="projects-projectStatusRel" name="projectStatusRel" v-model="projects.projectStatusRelId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectStatusOption.id" v-for="projectStatusOption in projectStatuses" :key="projectStatusOption.id">{{projectStatusOption.description}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.projects.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./projects-update.component.ts">
</script>
