<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="attachmentOthers">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.detail.title')">AttachmentOthers</span> {{attachmentOthers.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{attachmentOthers.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{attachmentOthers.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="attachmentOthers.content">
                            <a v-on:click="openFile(attachmentOthers.contentContentType, attachmentOthers.content)" v-text="$t('entity.action.open')">open</a>
                            {{attachmentOthers.contentContentType}}, {{byteSize(attachmentOthers.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{attachmentOthers.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{attachmentOthers.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentOthers.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="attachmentOthers.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: attachmentOthers.rfiId}}">{{attachmentOthers.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="attachmentOthers.id" :to="{name: 'AttachmentOthersEdit', params: {attachmentOthersId: attachmentOthers.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./attachment-others-details.component.ts">
</script>
