<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="documents">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.detail.title')">Documents</span> {{documents.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{documents.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{documents.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="documents.content">
                            <a v-on:click="openFile(documents.contentContentType, documents.content)" v-text="$t('entity.action.open')">open</a>
                            {{documents.contentContentType}}, {{byteSize(documents.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{documents.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceDocuments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{documents.creationDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="documents.id" :to="{name: 'DocumentsEdit', params: {documentsId: documents.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./documents-details.component.ts">
</script>
