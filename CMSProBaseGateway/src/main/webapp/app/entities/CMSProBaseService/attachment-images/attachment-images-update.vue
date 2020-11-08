<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.home.createOrEditLabel')">Create or edit a AttachmentImages</h2>
                <div>
                    <div class="form-group" v-if="attachmentImages.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="attachmentImages.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.fileName')" for="attachment-images-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="attachment-images-fileName"
                            :class="{'valid': !$v.attachmentImages.fileName.$invalid, 'invalid': $v.attachmentImages.fileName.$invalid }" v-model="$v.attachmentImages.fileName.$model"  required/>
                        <div v-if="$v.attachmentImages.fileName.$anyDirty && $v.attachmentImages.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentImages.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.fileType')" for="attachment-images-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="attachment-images-fileType"
                            :class="{'valid': !$v.attachmentImages.fileType.$invalid, 'invalid': $v.attachmentImages.fileType.$invalid }" v-model="$v.attachmentImages.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.content')" for="attachment-images-content">Content</label>
                        <div>
                            <div v-if="attachmentImages.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(attachmentImages.contentContentType, attachmentImages.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{attachmentImages.contentContentType}}, {{byteSize(attachmentImages.content)}}</span>
                                <button type="button" v-on:click="attachmentImages.content=null;attachmentImages.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, attachmentImages, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="attachment-images-content"
                            :class="{'valid': !$v.attachmentImages.content.$invalid, 'invalid': $v.attachmentImages.content.$invalid }" v-model="$v.attachmentImages.content.$model" />
                        <input type="hidden" class="form-control" name="contentContentType" id="attachment-images-contentContentType"
                            v-model="attachmentImages.contentContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.createdBy')" for="attachment-images-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="attachment-images-createdBy"
                            :class="{'valid': !$v.attachmentImages.createdBy.$invalid, 'invalid': $v.attachmentImages.createdBy.$invalid }" v-model="$v.attachmentImages.createdBy.$model"  required/>
                        <div v-if="$v.attachmentImages.createdBy.$anyDirty && $v.attachmentImages.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentImages.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.creationDate')" for="attachment-images-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="attachment-images-creationDate"
                                    v-model="$v.attachmentImages.creationDate.$model"
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
                            <b-form-input id="attachment-images-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.attachmentImages.creationDate.$invalid, 'invalid': $v.attachmentImages.creationDate.$invalid }"
                            v-model="$v.attachmentImages.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.attachmentImages.creationDate.$anyDirty && $v.attachmentImages.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.attachmentImages.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.rfi')" for="attachment-images-rfi">Rfi</label>
                        <select class="form-control" id="attachment-images-rfi" name="rfi" v-model="attachmentImages.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.attachmentImages.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./attachment-images-update.component.ts">
</script>
