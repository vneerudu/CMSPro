<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.home.createOrEditLabel')">Create or edit a TaskAttachmentOthers</h2>
                <div>
                    <div class="form-group" v-if="taskAttachmentOthers.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskAttachmentOthers.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileName')" for="task-attachment-others-fileName">File Name</label>
                        <input type="text" class="form-control" name="fileName" id="task-attachment-others-fileName"
                            :class="{'valid': !$v.taskAttachmentOthers.fileName.$invalid, 'invalid': $v.taskAttachmentOthers.fileName.$invalid }" v-model="$v.taskAttachmentOthers.fileName.$model"  required/>
                        <div v-if="$v.taskAttachmentOthers.fileName.$anyDirty && $v.taskAttachmentOthers.fileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentOthers.fileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileType')" for="task-attachment-others-fileType">File Type</label>
                        <input type="text" class="form-control" name="fileType" id="task-attachment-others-fileType"
                            :class="{'valid': !$v.taskAttachmentOthers.fileType.$invalid, 'invalid': $v.taskAttachmentOthers.fileType.$invalid }" v-model="$v.taskAttachmentOthers.fileType.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.content')" for="task-attachment-others-content">Content</label>
                        <div>
                            <div v-if="taskAttachmentOthers.content" class="form-text text-danger clearfix">
                                <a class="pull-left" v-on:click="openFile(taskAttachmentOthers.contentContentType, taskAttachmentOthers.content)" v-text="$t('entity.action.open')">open</a><br>
                                <span class="pull-left">{{taskAttachmentOthers.contentContentType}}, {{byteSize(taskAttachmentOthers.content)}}</span>
                                <button type="button" v-on:click="taskAttachmentOthers.content=null;taskAttachmentOthers.contentContentType=null;"
                                        class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_content" id="file_content" v-on:change="setFileData($event, taskAttachmentOthers, 'content', false)" v-text="$t('entity.action.addblob')"/>
                        </div>
                        <input type="hidden" class="form-control" name="content" id="task-attachment-others-content"
                            :class="{'valid': !$v.taskAttachmentOthers.content.$invalid, 'invalid': $v.taskAttachmentOthers.content.$invalid }" v-model="$v.taskAttachmentOthers.content.$model" />
                        <input type="hidden" class="form-control" name="contentContentType" id="task-attachment-others-contentContentType"
                            v-model="taskAttachmentOthers.contentContentType" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.createdBy')" for="task-attachment-others-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="task-attachment-others-createdBy"
                            :class="{'valid': !$v.taskAttachmentOthers.createdBy.$invalid, 'invalid': $v.taskAttachmentOthers.createdBy.$invalid }" v-model="$v.taskAttachmentOthers.createdBy.$model"  required/>
                        <div v-if="$v.taskAttachmentOthers.createdBy.$anyDirty && $v.taskAttachmentOthers.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentOthers.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.creationDate')" for="task-attachment-others-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="task-attachment-others-creationDate"
                                    v-model="$v.taskAttachmentOthers.creationDate.$model"
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
                            <b-form-input id="task-attachment-others-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.taskAttachmentOthers.creationDate.$invalid, 'invalid': $v.taskAttachmentOthers.creationDate.$invalid }"
                            v-model="$v.taskAttachmentOthers.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.taskAttachmentOthers.creationDate.$anyDirty && $v.taskAttachmentOthers.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskAttachmentOthers.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.task')" for="task-attachment-others-task">Task</label>
                        <select class="form-control" id="task-attachment-others-task" name="task" v-model="taskAttachmentOthers.taskId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="tasksOption.id" v-for="tasksOption in tasks" :key="tasksOption.id">{{tasksOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.rfi')" for="task-attachment-others-rfi">Rfi</label>
                        <select class="form-control" id="task-attachment-others-rfi" name="rfi" v-model="taskAttachmentOthers.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskAttachmentOthers.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-attachment-others-update.component.ts">
</script>
