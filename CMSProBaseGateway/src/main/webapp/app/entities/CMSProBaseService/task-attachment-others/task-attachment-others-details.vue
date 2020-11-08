<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskAttachmentOthers">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.detail.title')">TaskAttachmentOthers</span> {{taskAttachmentOthers.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentOthers.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentOthers.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentOthers.content">
                            <a v-on:click="openFile(taskAttachmentOthers.contentContentType, taskAttachmentOthers.content)" v-text="$t('entity.action.open')">open</a>
                            {{taskAttachmentOthers.contentContentType}}, {{byteSize(taskAttachmentOthers.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentOthers.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentOthers.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.task')">Task</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentOthers.taskId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: taskAttachmentOthers.taskId}}">{{taskAttachmentOthers.taskTitle}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentOthers.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentOthers.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: taskAttachmentOthers.rfiId}}">{{taskAttachmentOthers.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskAttachmentOthers.id" :to="{name: 'TaskAttachmentOthersEdit', params: {taskAttachmentOthersId: taskAttachmentOthers.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-attachment-others-details.component.ts">
</script>
