<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="projectTeams">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.detail.title')">ProjectTeams</span> {{projectTeams.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{projectTeams.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{projectTeams.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{projectTeams.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjectTeams.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="projectTeams.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: projectTeams.projectId}}">{{projectTeams.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="projectTeams.id" :to="{name: 'ProjectTeamsEdit', params: {projectTeamsId: projectTeams.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./project-teams-details.component.ts">
</script>
