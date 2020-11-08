<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="attachmentImages">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.detail.title')">AttachmentImages</span> {{attachmentImages.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{attachmentImages.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{attachmentImages.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="attachmentImages.content">
                            <a v-on:click="openFile(attachmentImages.contentContentType, attachmentImages.content)" v-text="$t('entity.action.open')">open</a>
                            {{attachmentImages.contentContentType}}, {{byteSize(attachmentImages.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{attachmentImages.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{attachmentImages.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachmentImages.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="attachmentImages.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: attachmentImages.rfiId}}">{{attachmentImages.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="attachmentImages.id" :to="{name: 'AttachmentImagesEdit', params: {attachmentImagesId: attachmentImages.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./attachment-images-details.component.ts">
</script>
