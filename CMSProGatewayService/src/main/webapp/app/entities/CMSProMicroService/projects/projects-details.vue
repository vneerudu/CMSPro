<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="projects">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.detail.title')">Projects</span> {{projects.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.projectID')">Project ID</span>
                    </dt>
                    <dd>
                        <span>{{projects.projectID}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{projects.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.department')">Department</span>
                    </dt>
                    <dd>
                        <span>{{projects.department}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.organization')">Organization</span>
                    </dt>
                    <dd>
                        <span>{{projects.organization}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{projects.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.finishDate')">Finish Date</span>
                    </dt>
                    <dd>
                        <span>{{projects.finishDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjects.projectStatusRel')">Project Status Rel</span>
                    </dt>
                    <dd>
                        <div v-if="projects.projectStatusRelId">
                            <router-link :to="{name: 'ProjectStatusView', params: {projectStatusId: projects.projectStatusRelId}}">{{projects.projectStatusRelDescription}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="projects.id" :to="{name: 'ProjectsEdit', params: {projectsId: projects.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./projects-details.component.ts">
</script>
