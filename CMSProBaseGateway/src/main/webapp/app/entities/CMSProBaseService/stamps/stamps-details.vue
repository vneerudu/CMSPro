<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="stamps">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.detail.title')">Stamps</span> {{stamps.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.stamp')">Stamp</span>
                    </dt>
                    <dd>
                        <span>{{stamps.stamp}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{stamps.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{stamps.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{stamps.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceStamps.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="stamps.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: stamps.projectId}}">{{stamps.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="stamps.id" :to="{name: 'StampsEdit', params: {stampsId: stamps.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stamps-details.component.ts">
</script>
