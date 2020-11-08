<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.home.createOrEditLabel')">Create or edit a TaskAttachmentImages</h2>
                <div>
                    <div class="form-group" v-if="taskAttachmentImages.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskAttachmentImages.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileName')" for="task-attachment-images-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="task-attachment-images-fileName"
                            :class="{'valid': !$v.taskAttachmentImages.fileName.$invalid, 'invalid': $v.taskAttachmentImages.fileName.$invalid }" v-model="$v.taskAttachmentImages.fileName.$model"  required/>
                        <div v-if="$v.taskAttachmentImages.fileName.$anyDirty && $v.taskAttachmentImages.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentImages.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileType')" for="task-attachment-images-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="task-attachment-images-fileType"
                            :class="{'valid': !$v.taskAttachmentImages.fileType.$invalid, 'invalid': $v.taskAttachmentImages.fileType.$invalid }" v-model="$v.taskAttachmentImages.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.content')" for="task-attachment-images-content">Content</label>
                        <div>
                            <div v-if="taskAttachmentImages.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(taskAttachmentImages.contentContentType, taskAttachmentImages.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{taskAttachmentImages.contentContentType}}, {{byteSize(taskAttachmentImages.content)}}</span>
                                <button type="button" v-on:click="taskAttachmentImages.content=null;taskAttachmentImages.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, taskAttachmentImages, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="task-attachment-images-content"
                            :class="{'valid': !$v.taskAttachmentImages.content.$invalid, 'invalid': $v.taskAttachmentImages.content.$invalid }" v-model="$v.taskAttachmentImages.content.$model" />
                        <input type="hidden" class="form-control" name="contentContentType" id="task-attachment-images-contentContentType"
                            v-model="taskAttachmentImages.contentContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.createdBy')" for="task-attachment-images-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="task-attachment-images-createdBy"
                            :class="{'valid': !$v.taskAttachmentImages.createdBy.$invalid, 'invalid': $v.taskAttachmentImages.createdBy.$invalid }" v-model="$v.taskAttachmentImages.createdBy.$model"  required/>
                        <div v-if="$v.taskAttachmentImages.createdBy.$anyDirty && $v.taskAttachmentImages.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentImages.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.creationDate')" for="task-attachment-images-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="task-attachment-images-creationDate"
                                    v-model="$v.taskAttachmentImages.creationDate.$model"
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
                            <b-form-input id="task-attachment-images-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.taskAttachmentImages.creationDate.$invalid, 'invalid': $v.taskAttachmentImages.creationDate.$invalid }"
                            v-model="$v.taskAttachmentImages.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.taskAttachmentImages.creationDate.$anyDirty && $v.taskAttachmentImages.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentImages.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.task')" for="task-attachment-images-task">Task</label>
                        <select class="form-control" id="task-attachment-images-task" name="task" v-model="taskAttachmentImages.taskId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="tasksOption.id" v-for="tasksOption in tasks" :key="tasksOption.id">{{tasksOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.rfi')" for="task-attachment-images-rfi">Rfi</label>
                        <select class="form-control" id="task-attachment-images-rfi" name="rfi" v-model="taskAttachmentImages.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskAttachmentImages.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-attachment-images-update.component.ts">
</script>
