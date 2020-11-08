<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.home.createOrEditLabel')">Create or edit a Sheets</h2>
                <div>
                    <div class="form-group" v-if="sheets.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sheets.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.number')" for="sheets-number">Number</label>
                        <input type="number" class="form-control" name="number" id="sheets-number"
                            :class="{'valid': !$v.sheets.number.$invalid, 'invalid': $v.sheets.number.$invalid }" v-model.number="$v.sheets.number.$model"  required/>
                        <div v-if="$v.sheets.number.$anyDirty && $v.sheets.number.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheets.number.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.sheets.number.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.title')" for="sheets-title">Title</label>
                        <input type="text" class="form-control" name="title" id="sheets-title"
                            :class="{'valid': !$v.sheets.title.$invalid, 'invalid': $v.sheets.title.$invalid }" v-model="$v.sheets.title.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.version')" for="sheets-version">Version</label>
                        <input type="text" class="form-control" name="version" id="sheets-version"
                            :class="{'valid': !$v.sheets.version.$invalid, 'invalid': $v.sheets.version.$invalid }" v-model="$v.sheets.version.$model"  required/>
                        <div v-if="$v.sheets.version.$anyDirty && $v.sheets.version.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheets.version.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.createdBy')" for="sheets-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="sheets-createdBy"
                            :class="{'valid': !$v.sheets.createdBy.$invalid, 'invalid': $v.sheets.createdBy.$invalid }" v-model="$v.sheets.createdBy.$model"  required/>
                        <div v-if="$v.sheets.createdBy.$anyDirty && $v.sheets.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheets.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.creationDate')" for="sheets-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="sheets-creationDate"
                                    v-model="$v.sheets.creationDate.$model"
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
                            <b-form-input id="sheets-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.sheets.creationDate.$invalid, 'invalid': $v.sheets.creationDate.$invalid }"
                            v-model="$v.sheets.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.sheets.creationDate.$anyDirty && $v.sheets.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheets.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.documents')" for="sheets-documents">Documents</label>
                        <select class="form-control" id="sheets-documents" name="documents" v-model="sheets.documentsId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="documentsOption.id" v-for="documentsOption in documents" :key="documentsOption.id">{{documentsOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.project')" for="sheets-project">Project</label>
                        <select class="form-control" id="sheets-project" name="project" v-model="sheets.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sheets.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sheets-update.component.ts">
</script>
