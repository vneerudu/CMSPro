<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.home.createOrEditLabel')">Create or edit a RFI</h2>
                <div>
                    <div class="form-group" v-if="rFI.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rFI.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.title')" for="rfi-title">Title</label>
                        <input type="text" class="form-control" name="title" id="rfi-title"
                            :class="{'valid': !$v.rFI.title.$invalid, 'invalid': $v.rFI.title.$invalid }" v-model="$v.rFI.title.$model"  required/>
                        <div v-if="$v.rFI.title.$anyDirty && $v.rFI.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFI.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.question')" for="rfi-question">Question</label>
                        <input type="text" class="form-control" name="question" id="rfi-question"
                            :class="{'valid': !$v.rFI.question.$invalid, 'invalid': $v.rFI.question.$invalid }" v-model="$v.rFI.question.$model"  required/>
                        <div v-if="$v.rFI.question.$anyDirty && $v.rFI.question.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFI.question.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.answer')" for="rfi-answer">Answer</label>
                        <input type="text" class="form-control" name="answer" id="rfi-answer"
                            :class="{'valid': !$v.rFI.answer.$invalid, 'invalid': $v.rFI.answer.$invalid }" v-model="$v.rFI.answer.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.sentDate')" for="rfi-sentDate">Sent Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="rfi-sentDate"
                                    v-model="$v.rFI.sentDate.$model"
                                    name="sentDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="rfi-sentDate" type="text" class="form-control" name="sentDate"  :class="{'valid': !$v.rFI.sentDate.$invalid, 'invalid': $v.rFI.sentDate.$invalid }"
                            v-model="$v.rFI.sentDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.dueDate')" for="rfi-dueDate">Due Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="rfi-dueDate"
                                    v-model="$v.rFI.dueDate.$model"
                                    name="dueDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="rfi-dueDate" type="text" class="form-control" name="dueDate"  :class="{'valid': !$v.rFI.dueDate.$invalid, 'invalid': $v.rFI.dueDate.$invalid }"
                            v-model="$v.rFI.dueDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.locked')" for="rfi-locked">Locked</label>
                        <input type="checkbox" class="form-check" name="locked" id="rfi-locked"
                            :class="{'valid': !$v.rFI.locked.$invalid, 'invalid': $v.rFI.locked.$invalid }" v-model="$v.rFI.locked.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.lockedBy')" for="rfi-lockedBy">Locked By</label>
                        <input type="text" class="form-control" name="lockedBy" id="rfi-lockedBy"
                            :class="{'valid': !$v.rFI.lockedBy.$invalid, 'invalid': $v.rFI.lockedBy.$invalid }" v-model="$v.rFI.lockedBy.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.status')" for="rfi-status">Status</label>
                        <select class="form-control" id="rfi-status" name="status" v-model="rFI.statusId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIStatusesOption.id" v-for="rFIStatusesOption in rFIStatuses" :key="rFIStatusesOption.id">{{rFIStatusesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.project')" for="rfi-project">Project</label>
                        <select class="form-control" id="rfi-project" name="project" v-model="rFI.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rFI.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rfi-update.component.ts">
</script>
