<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="projectStatus">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.detail.title')">ProjectStatus</span> {{projectStatus.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{projectStatus.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{projectStatus.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatus.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{projectStatus.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="projectStatus.id" :to="{name: 'ProjectStatusEdit', params: {projectStatusId: projectStatus.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./project-status-details.component.ts">
</script>
