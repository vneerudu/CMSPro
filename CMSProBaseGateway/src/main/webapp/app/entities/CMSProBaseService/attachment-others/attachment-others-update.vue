<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.home.createOrEditLabel')">Create or edit a AttachmentOthers</h2>
                <div>
                    <div class="form-group" v-if="attachmentOthers.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="attachmentOthers.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileName')" for="attachment-others-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="attachment-others-fileName"
                            :class="{'valid': !$v.attachmentOthers.fileName.$invalid, 'invalid': $v.attachmentOthers.fileName.$invalid }" v-model="$v.attachmentOthers.fileName.$model"  required/>
                        <div v-if="$v.attachmentOthers.fileName.$anyDirty && $v.attachmentOthers.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentOthers.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileType')" for="attachment-others-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="attachment-others-fileType"
                            :class="{'valid': !$v.attachmentOthers.fileType.$invalid, 'invalid': $v.attachmentOthers.fileType.$invalid }" v-model="$v.attachmentOthers.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.content')" for="attachment-others-content">Content</label>
                        <div>
                            <div v-if="attachmentOthers.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(attachmentOthers.contentContentType, attachmentOthers.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{attachmentOthers.contentContentType}}, {{byteSize(attachmentOthers.content)}}</span>
                                <button type="button" v-on:click="attachmentOthers.content=null;attachmentOthers.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, attachmentOthers, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="attachment-others-content"
                            :class="{'valid': !$v.attachmentOthers.content.$invalid, 'invalid': $v.attachmentOthers.content.$invalid }" v-model="$v.attachmentOthers.content.$model" />
                        <input type="hidden" class="form-control" name="contentContentType" id="attachment-others-contentContentType"
                            v-model="attachmentOthers.contentContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.createdBy')" for="attachment-others-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="attachment-others-createdBy"
                            :class="{'valid': !$v.attachmentOthers.createdBy.$invalid, 'invalid': $v.attachmentOthers.createdBy.$invalid }" v-model="$v.attachmentOthers.createdBy.$model"  required/>
                        <div v-if="$v.attachmentOthers.createdBy.$anyDirty && $v.attachmentOthers.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentOthers.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.creationDate')" for="attachment-others-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="attachment-others-creationDate"
                                    v-model="$v.attachmentOthers.creationDate.$model"
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
                            <b-form-input id="attachment-others-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.attachmentOthers.creationDate.$invalid, 'invalid': $v.attachmentOthers.creationDate.$invalid }"
                            v-model="$v.attachmentOthers.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.attachmentOthers.creationDate.$anyDirty && $v.attachmentOthers.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentOthers.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.rfi')" for="attachment-others-rfi">Rfi</label>
                        <select class="form-control" id="attachment-others-rfi" name="rfi" v-model="attachmentOthers.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.attachmentOthers.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./attachment-others-update.component.ts">
</script>
