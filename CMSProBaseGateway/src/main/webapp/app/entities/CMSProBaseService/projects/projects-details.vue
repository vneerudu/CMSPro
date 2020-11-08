<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="projects">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.detail.title')">Projects</span> {{projects.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{projects.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{projects.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{projects.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.finishDate')">Finish Date</span>
                    </dt>
                    <dd>
                        <span>{{projects.finishDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.lastUpdate')">Last Update</span>
                    </dt>
                    <dd>
                        <span>{{projects.lastUpdate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{projects.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{projects.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceProjects.account')">Account</span>
                    </dt>
                    <dd>
                        <div v-if="projects.accountId">
                            <router-link :to="{name: 'AccountsView', params: {accountsId: projects.accountId}}">{{projects.accountAccountNumber}}</router-link>
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
