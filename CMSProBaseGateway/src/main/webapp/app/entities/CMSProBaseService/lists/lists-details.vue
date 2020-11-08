<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="lists">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.detail.title')">Lists</span> {{lists.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{lists.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{lists.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{lists.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLists.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="lists.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: lists.projectId}}">{{lists.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="lists.id" :to="{name: 'ListsEdit', params: {listsId: lists.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./lists-details.component.ts">
</script>
