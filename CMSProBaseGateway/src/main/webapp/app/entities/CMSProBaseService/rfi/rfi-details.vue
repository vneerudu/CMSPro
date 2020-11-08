<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rFI">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.detail.title')">RFI</span> {{rFI.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{rFI.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.question')">Question</span>
                    </dt>
                    <dd>
                        <span>{{rFI.question}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.answer')">Answer</span>
                    </dt>
                    <dd>
                        <span>{{rFI.answer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.sentDate')">Sent Date</span>
                    </dt>
                    <dd>
                        <span>{{rFI.sentDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.dueDate')">Due Date</span>
                    </dt>
                    <dd>
                        <span>{{rFI.dueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.locked')">Locked</span>
                    </dt>
                    <dd>
                        <span>{{rFI.locked}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.lockedBy')">Locked By</span>
                    </dt>
                    <dd>
                        <span>{{rFI.lockedBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.status')">Status</span>
                    </dt>
                    <dd>
                        <div v-if="rFI.statusId">
                            <router-link :to="{name: 'RFIStatusesView', params: {rFIStatusesId: rFI.statusId}}">{{rFI.statusDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFi.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="rFI.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: rFI.projectId}}">{{rFI.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rFI.id" :to="{name: 'RFIEdit', params: {rFIId: rFI.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi-details.component.ts">
</script>
