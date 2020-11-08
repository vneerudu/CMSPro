<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceDocuments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.home.createOrEditLabel')">Create or edit a Documents</h2>
                <div>
                    <div class="form-group" v-if="documents.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="documents.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.fileName')" for="documents-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="documents-fileName"
                            :class="{'valid': !$v.documents.fileName.$invalid, 'invalid': $v.documents.fileName.$invalid }" v-model="$v.documents.fileName.$model"  required/>
                        <div v-if="$v.documents.fileName.$anyDirty && $v.documents.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documents.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.fileType')" for="documents-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="documents-fileType"
                            :class="{'valid': !$v.documents.fileType.$invalid, 'invalid': $v.documents.fileType.$invalid }" v-model="$v.documents.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.content')" for="documents-content">Content</label>
                        <div>
                            <div v-if="documents.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(documents.contentContentType, documents.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{documents.contentContentType}}, {{byteSize(documents.content)}}</span>
                                <button type="button" v-on:click="documents.content=null;documents.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, documents, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="documents-content"
                            :class="{'valid': !$v.documents.content.$invalid, 'invalid': $v.documents.content.$invalid }" v-model="$v.documents.content.$model"  required/>
                        <input type="hidden" class="form-control" name="contentContentType" id="documents-contentContentType"
                            v-model="documents.contentContentType" />
                        <div v-if="$v.documents.content.$anyDirty && $v.documents.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documents.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.createdBy')" for="documents-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="documents-createdBy"
                            :class="{'valid': !$v.documents.createdBy.$invalid, 'invalid': $v.documents.createdBy.$invalid }" v-model="$v.documents.createdBy.$model"  required/>
                        <div v-if="$v.documents.createdBy.$anyDirty && $v.documents.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documents.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.creationDate')" for="documents-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="documents-creationDate"
                                    v-model="$v.documents.creationDate.$model"
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
                            <b-form-input id="documents-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.documents.creationDate.$invalid, 'invalid': $v.documents.creationDate.$invalid }"
                            v-model="$v.documents.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.documents.creationDate.$anyDirty && $v.documents.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.documents.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.documents.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./documents-update.component.ts">
</script>
