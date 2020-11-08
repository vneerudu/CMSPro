<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.home.createOrEditLabel')">Create or edit a Projects</h2>
                <div>
                    <div class="form-group" v-if="projects.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="projects.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.code')" for="projects-code">Code</label>
                        <input type="text" class="form-control" name="code" id="projects-code"
                            :class="{'valid': !$v.projects.code.$invalid, 'invalid': $v.projects.code.$invalid }" v-model="$v.projects.code.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.name')" for="projects-name">Name</label>
                        <input type="text" class="form-control" name="name" id="projects-name"
                            :class="{'valid': !$v.projects.name.$invalid, 'invalid': $v.projects.name.$invalid }" v-model="$v.projects.name.$model"  required/>
                        <div v-if="$v.projects.name.$anyDirty && $v.projects.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.startDate')" for="projects-startDate">Start Date</label>
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
                            v-model="$v.projects.startDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.finishDate')" for="projects-finishDate">Finish Date</label>
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
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.lastUpdate')" for="projects-lastUpdate">Last Update</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="projects-lastUpdate"
                                    v-model="$v.projects.lastUpdate.$model"
                                    name="lastUpdate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="projects-lastUpdate" type="text" class="form-control" name="lastUpdate"  :class="{'valid': !$v.projects.lastUpdate.$invalid, 'invalid': $v.projects.lastUpdate.$invalid }"
                            v-model="$v.projects.lastUpdate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.createdBy')" for="projects-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="projects-createdBy"
                            :class="{'valid': !$v.projects.createdBy.$invalid, 'invalid': $v.projects.createdBy.$invalid }" v-model="$v.projects.createdBy.$model"  required/>
                        <div v-if="$v.projects.createdBy.$anyDirty && $v.projects.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.creationDate')" for="projects-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="projects-creationDate"
                                    v-model="$v.projects.creationDate.$model"
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
                            <b-form-input id="projects-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.projects.creationDate.$invalid, 'invalid': $v.projects.creationDate.$invalid }"
                            v-model="$v.projects.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.projects.creationDate.$anyDirty && $v.projects.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.projects.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.account')" for="projects-account">Account</label>
                        <select class="form-control" id="projects-account" name="account" v-model="projects.accountId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="accountsOption.id" v-for="accountsOption in accounts" :key="accountsOption.id">{{accountsOption.accountNumber}}</option>
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
