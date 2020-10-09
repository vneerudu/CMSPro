<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="projectStatuses">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.detail.title')">ProjectStatuses</span> {{projectStatuses.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.status_id')">Status Id</span>
                    </dt>
                    <dd>
                        <span>{{projectStatuses.status_id}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.status_code')">Status Code</span>
                    </dt>
                    <dd>
                        <span>{{projectStatuses.status_code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{projectStatuses.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProGatewayServiceApp.cmsProMicroServiceProjectStatuses.is_active')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{projectStatuses.is_active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="projectStatuses.id" :to="{name: 'ProjectStatusesEdit', params: {projectStatusesId: projectStatuses.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./project-statuses-details.component.ts">
</script>
