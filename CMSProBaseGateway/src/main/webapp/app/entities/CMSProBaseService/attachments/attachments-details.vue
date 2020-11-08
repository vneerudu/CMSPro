<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="attachments">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.detail.title')">Attachments</span> {{attachments.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.folder')">Folder</span>
                    </dt>
                    <dd>
                        <span>{{attachments.folder}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{attachments.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{attachments.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{attachments.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.image')">Image</span>
                    </dt>
                    <dd>
                        <div v-if="attachments.imageId">
                            <router-link :to="{name: 'AttachmentImagesView', params: {attachmentImagesId: attachments.imageId}}">{{attachments.imageFileName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.pdfattachment')">Pdfattachment</span>
                    </dt>
                    <dd>
                        <div v-if="attachments.pdfattachmentId">
                            <router-link :to="{name: 'AttachmentOthersView', params: {attachmentOthersId: attachments.pdfattachmentId}}">{{attachments.pdfattachmentFileName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAttachments.sheet')">Sheet</span>
                    </dt>
                    <dd>
                        <div v-if="attachments.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: attachments.sheetId}}">{{attachments.sheetNumber}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="attachments.id" :to="{name: 'AttachmentsEdit', params: {attachmentsId: attachments.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./attachments-details.component.ts">
</script>
