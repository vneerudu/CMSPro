<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.home.createOrEditLabel')">Create or edit a TaskComments</h2>
                <div>
                    <div class="form-group" v-if="taskComments.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="taskComments.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.createdBy')" for="task-comments-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="task-comments-createdBy"
                            :class="{'valid': !$v.taskComments.createdBy.$invalid, 'invalid': $v.taskComments.createdBy.$invalid }" v-model="$v.taskComments.createdBy.$model"  required/>
                        <div v-if="$v.taskComments.createdBy.$anyDirty && $v.taskComments.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskComments.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.comment')" for="task-comments-comment">Comment</label>
                        <input type="text" class="form-control" name="comment" id="task-comments-comment"
                            :class="{'valid': !$v.taskComments.comment.$invalid, 'invalid': $v.taskComments.comment.$invalid }" v-model="$v.taskComments.comment.$model"  required/>
                        <div v-if="$v.taskComments.comment.$anyDirty && $v.taskComments.comment.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskComments.comment.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.creationDate')" for="task-comments-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="task-comments-creationDate"
                                    v-model="$v.taskComments.creationDate.$model"
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
                            <b-form-input id="task-comments-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.taskComments.creationDate.$invalid, 'invalid': $v.taskComments.creationDate.$invalid }"
                            v-model="$v.taskComments.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.taskComments.creationDate.$anyDirty && $v.taskComments.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.taskComments.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.task')" for="task-comments-task">Task</label>
                        <select class="form-control" id="task-comments-task" name="task" v-model="taskComments.taskId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="tasksOption.id" v-for="tasksOption in tasks" :key="tasksOption.id">{{tasksOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.taskComments.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./task-comments-update.component.ts">
</script>
