<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sheets">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.detail.title')">Sheets</span> {{sheets.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{sheets.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{sheets.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.version')">Version</span>
                    </dt>
                    <dd>
                        <span>{{sheets.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{sheets.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{sheets.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.documents')">Documents</span>
                    </dt>
                    <dd>
                        <div v-if="sheets.documentsId">
                            <router-link :to="{name: 'DocumentsView', params: {documentsId: sheets.documentsId}}">{{sheets.documentsId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="sheets.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: sheets.projectId}}">{{sheets.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sheets.id" :to="{name: 'SheetsEdit', params: {sheetsId: sheets.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheets-details.component.ts">
</script>
