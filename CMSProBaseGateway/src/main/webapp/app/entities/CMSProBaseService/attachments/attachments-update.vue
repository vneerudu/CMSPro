<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.home.createOrEditLabel')">Create or edit a Attachments</h2>
                <div>
                    <div class="form-group" v-if="attachments.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="attachments.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.folder')" for="attachments-folder">Folder</label>
                        <input type="text" class="form-control" name="folder" id="attachments-folder"
                            :class="{'valid': !$v.attachments.folder.$invalid, 'invalid': $v.attachments.folder.$invalid }" v-model="$v.attachments.folder.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.fileName')" for="attachments-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="attachments-fileName"
                            :class="{'valid': !$v.attachments.fileName.$invalid, 'invalid': $v.attachments.fileName.$invalid }" v-model="$v.attachments.fileName.$model"  required/>
                        <div v-if="$v.attachments.fileName.$anyDirty && $v.attachments.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachments.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.createdBy')" for="attachments-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="attachments-createdBy"
                            :class="{'valid': !$v.attachments.createdBy.$invalid, 'invalid': $v.attachments.createdBy.$invalid }" v-model="$v.attachments.createdBy.$model"  required/>
                        <div v-if="$v.attachments.createdBy.$anyDirty && $v.attachments.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachments.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.creationDate')" for="attachments-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="attachments-creationDate"
                                    v-model="$v.attachments.creationDate.$model"
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
                            <b-form-input id="attachments-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.attachments.creationDate.$invalid, 'invalid': $v.attachments.creationDate.$invalid }"
                            v-model="$v.attachments.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.attachments.creationDate.$anyDirty && $v.attachments.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachments.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.image')" for="attachments-image">Image</label>
                        <select class="form-control" id="attachments-image" name="image" v-model="attachments.imageId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="attachmentImagesOption.id" v-for="attachmentImagesOption in attachmentImages" :key="attachmentImagesOption.id">{{attachmentImagesOption.fileName}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.pdfattachment')" for="attachments-pdfattachment">Pdfattachment</label>
                        <select class="form-control" id="attachments-pdfattachment" name="pdfattachment" v-model="attachments.pdfattachmentId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="attachmentOthersOption.id" v-for="attachmentOthersOption in attachmentOthers" :key="attachmentOthersOption.id">{{attachmentOthersOption.fileName}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.sheet')" for="attachments-sheet">Sheet</label>
                        <select class="form-control" id="attachments-sheet" name="sheet" v-model="attachments.sheetId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="sheetsOption.id" v-for="sheetsOption in sheets" :key="sheetsOption.id">{{sheetsOption.number}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.attachments.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./attachments-update.component.ts">
</script>
