<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskAttachments">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.detail.title')">TaskAttachments</span> {{taskAttachments.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.folder')">Folder</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachments.folder}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachments.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachments.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskAttachments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{taskAttachments.creationDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskAttachments.id" :to="{name: 'TaskAttachmentsEdit', params: {taskAttachmentsId: taskAttachments.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-attachments-details.component.ts">
</script>
