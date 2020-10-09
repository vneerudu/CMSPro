<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProGatewayServiceApp.cmsProMicroServiceProject.home.createOrEditLabel" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.home.createOrEditLabel')">Create or edit a Project</h2>
                <div>
                    <div class="form-group" v-if="project.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="project.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.name')" for="project-name">Name</label>
                        <input type="text" class="form-control" name="name" id="project-name"
                            :class="{'valid': !$v.project.name.$invalid, 'invalid': $v.project.name.$invalid }" v-model="$v.project.name.$model"  required/>
                        <div v-if="$v.project.name.$anyDirty && $v.project.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.project.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.organization')" for="project-organization">Organization</label>
                        <input type="text" class="form-control" name="organization" id="project-organization"
                            :class="{'valid': !$v.project.organization.$invalid, 'invalid': $v.project.organization.$invalid }" v-model="$v.project.organization.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.startDate')" for="project-startDate">Start Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="project-startDate"
                                    v-model="$v.project.startDate.$model"
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
                            <b-form-input id="project-startDate" type="text" class="form-control" name="startDate"  :class="{'valid': !$v.project.startDate.$invalid, 'invalid': $v.project.startDate.$invalid }"
                            v-model="$v.project.startDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.project.startDate.$anyDirty && $v.project.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.project.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProject.finishDate')" for="project-finishDate">Finish Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="project-finishDate"
                                    v-model="$v.project.finishDate.$model"
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
                            <b-form-input id="project-finishDate" type="text" class="form-control" name="finishDate"  :class="{'valid': !$v.project.finishDate.$invalid, 'invalid': $v.project.finishDate.$invalid }"
                            v-model="$v.project.finishDate.$model"  />
                        </b-input-group>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.project.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./project-update.component.ts">
</script>
