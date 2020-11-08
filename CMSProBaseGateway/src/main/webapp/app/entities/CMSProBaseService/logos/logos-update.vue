<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceLogos.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.home.createOrEditLabel')">Create or edit a Logos</h2>
                <div>
                    <div class="form-group" v-if="logos.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="logos.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.fileName')" for="logos-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="logos-fileName"
                            :class="{'valid': !$v.logos.fileName.$invalid, 'invalid': $v.logos.fileName.$invalid }" v-model="$v.logos.fileName.$model"  required/>
                        <div v-if="$v.logos.fileName.$anyDirty && $v.logos.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.logos.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.fileType')" for="logos-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="logos-fileType"
                            :class="{'valid': !$v.logos.fileType.$invalid, 'invalid': $v.logos.fileType.$invalid }" v-model="$v.logos.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.content')" for="logos-content">Content</label>
                        <div>
                            <div v-if="logos.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(logos.contentContentType, logos.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{logos.contentContentType}}, {{byteSize(logos.content)}}</span>
                                <button type="button" v-on:click="logos.content=null;logos.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, logos, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="logos-content"
                            :class="{'valid': !$v.logos.content.$invalid, 'invalid': $v.logos.content.$invalid }" v-model="$v.logos.content.$model"  required/>
                        <input type="hidden" class="form-control" name="contentContentType" id="logos-contentContentType"
                            v-model="logos.contentContentType" />
                        <div v-if="$v.logos.content.$anyDirty && $v.logos.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.logos.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.createdBy')" for="logos-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="logos-createdBy"
                            :class="{'valid': !$v.logos.createdBy.$invalid, 'invalid': $v.logos.createdBy.$invalid }" v-model="$v.logos.createdBy.$model"  required/>
                        <div v-if="$v.logos.createdBy.$anyDirty && $v.logos.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.logos.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.creationDate')" for="logos-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="logos-creationDate"
                                    v-model="$v.logos.creationDate.$model"
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
                            <b-form-input id="logos-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.logos.creationDate.$invalid, 'invalid': $v.logos.creationDate.$invalid }"
                            v-model="$v.logos.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.logos.creationDate.$anyDirty && $v.logos.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.logos.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.logos.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./logos-update.component.ts">
</script>
