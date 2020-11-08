<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rFIStatuses">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.detail.title')">RFIStatuses</span> {{rFIStatuses.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{rFIStatuses.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{rFIStatuses.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiStatuses.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{rFIStatuses.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rFIStatuses.id" :to="{name: 'RFIStatusesEdit', params: {rFIStatusesId: rFIStatuses.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi-statuses-details.component.ts">
</script>
