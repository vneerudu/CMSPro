<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="logos">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.detail.title')">Logos</span> {{logos.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.fileName')">File Name</span>
                    </dt>
                    <dd>
                        <span>{{logos.fileName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.fileType')">File Type</span>
                    </dt>
                    <dd>
                        <span>{{logos.fileType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.content')">Content</span>
                    </dt>
                    <dd>
                        <div v-if="logos.content">
                            <a v-on:click="openFile(logos.contentContentType, logos.content)" v-text="$t('entity.action.open')">open</a>
                            {{logos.contentContentType}}, {{byteSize(logos.content)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{logos.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLogos.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{logos.creationDate}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="logos.id" :to="{name: 'LogosEdit', params: {logosId: logos.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./logos-details.component.ts">
</script>
