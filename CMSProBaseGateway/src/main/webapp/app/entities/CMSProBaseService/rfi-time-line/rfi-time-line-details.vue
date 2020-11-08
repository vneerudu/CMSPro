<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rFITimeLine">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.detail.title')">RFITimeLine</span> {{rFITimeLine.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{rFITimeLine.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.message')">Message</span>
                    </dt>
                    <dd>
                        <span>{{rFITimeLine.message}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{rFITimeLine.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="rFITimeLine.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: rFITimeLine.rfiId}}">{{rFITimeLine.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rFITimeLine.id" :to="{name: 'RFITimeLineEdit', params: {rFITimeLineId: rFITimeLine.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi-time-line-details.component.ts">
</script>
