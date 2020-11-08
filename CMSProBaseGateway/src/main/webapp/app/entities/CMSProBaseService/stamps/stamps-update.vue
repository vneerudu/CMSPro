<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceStamps.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.home.createOrEditLabel')">Create or edit a Stamps</h2>
                <div>
                    <div class="form-group" v-if="stamps.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stamps.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.stamp')" for="stamps-stamp">Stamp</label>
                        <input type="text" class="form-control" name="stamp" id="stamps-stamp"
                            :class="{'valid': !$v.stamps.stamp.$invalid, 'invalid': $v.stamps.stamp.$invalid }" v-model="$v.stamps.stamp.$model"  required/>
                        <div v-if="$v.stamps.stamp.$anyDirty && $v.stamps.stamp.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stamps.stamp.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.title')" for="stamps-title">Title</label>
                        <input type="text" class="form-control" name="title" id="stamps-title"
                            :class="{'valid': !$v.stamps.title.$invalid, 'invalid': $v.stamps.title.$invalid }" v-model="$v.stamps.title.$model"  required/>
                        <div v-if="$v.stamps.title.$anyDirty && $v.stamps.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stamps.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.createdBy')" for="stamps-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="stamps-createdBy"
                            :class="{'valid': !$v.stamps.createdBy.$invalid, 'invalid': $v.stamps.createdBy.$invalid }" v-model="$v.stamps.createdBy.$model"  required/>
                        <div v-if="$v.stamps.createdBy.$anyDirty && $v.stamps.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stamps.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.creationDate')" for="stamps-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="stamps-creationDate"
                                    v-model="$v.stamps.creationDate.$model"
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
                            <b-form-input id="stamps-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.stamps.creationDate.$invalid, 'invalid': $v.stamps.creationDate.$invalid }"
                            v-model="$v.stamps.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.stamps.creationDate.$anyDirty && $v.stamps.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stamps.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.project')" for="stamps-project">Project</label>
                        <select class="form-control" id="stamps-project" name="project" v-model="stamps.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stamps.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./stamps-update.component.ts">
</script>
