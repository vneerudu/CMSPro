<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskAttachmentImages">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.detail.title')">TaskAttachmentImages</span> {{taskAttachmentImages.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentImages.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentImages.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentImages.content">
                            <a v-on:click="openFile(taskAttachmentImages.contentContentType, taskAttachmentImages.content)" v-text="$t('entity.action.open')">open</a>
                            {{taskAttachmentImages.contentContentType}}, {{byteSize(taskAttachmentImages.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentImages.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachmentImages.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.task')">Task</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentImages.taskId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: taskAttachmentImages.taskId}}">{{taskAttachmentImages.taskTitle}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachmentImages.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="taskAttachmentImages.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: taskAttachmentImages.rfiId}}">{{taskAttachmentImages.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskAttachmentImages.id" :to="{name: 'TaskAttachmentImagesEdit', params: {taskAttachmentImagesId: taskAttachmentImages.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-attachment-images-details.component.ts">
</script>
