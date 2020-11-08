<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.home.createOrEditLabel')">Create or edit a TaskAttachments</h2>
                <div>
                    <div class="form-group" v-if="taskAttachments.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskAttachments.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.folder')" for="task-attachments-folder">Folder</label>
                        <input type="text" class="form-control" name="folder" id="task-attachments-folder"
                            :class="{'valid': !$v.taskAttachments.folder.$invalid, 'invalid': $v.taskAttachments.folder.$invalid }" v-model="$v.taskAttachments.folder.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.fileName')" for="task-attachments-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="task-attachments-fileName"
                            :class="{'valid': !$v.taskAttachments.fileName.$invalid, 'invalid': $v.taskAttachments.fileName.$invalid }" v-model="$v.taskAttachments.fileName.$model"  required/>
                        <div v-if="$v.taskAttachments.fileName.$anyDirty && $v.taskAttachments.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachments.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.createdBy')" for="task-attachments-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="task-attachments-createdBy"
                            :class="{'valid': !$v.taskAttachments.createdBy.$invalid, 'invalid': $v.taskAttachments.createdBy.$invalid }" v-model="$v.taskAttachments.createdBy.$model"  required/>
                        <div v-if="$v.taskAttachments.createdBy.$anyDirty && $v.taskAttachments.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachments.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.creationDate')" for="task-attachments-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="task-attachments-creationDate"
                                    v-model="$v.taskAttachments.creationDate.$model"
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
                            <b-form-input id="task-attachments-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.taskAttachments.creationDate.$invalid, 'invalid': $v.taskAttachments.creationDate.$invalid }"
                            v-model="$v.taskAttachments.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.taskAttachments.creationDate.$anyDirty && $v.taskAttachments.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachments.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskAttachments.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-attachments-update.component.ts">
</script>
