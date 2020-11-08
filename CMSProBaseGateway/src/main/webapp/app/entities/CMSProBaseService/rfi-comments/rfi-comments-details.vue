<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rFIComments">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.detail.title')">RFIComments</span> {{rFIComments.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{rFIComments.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.comment')">Comment</span>
                    </dt>
                    <dd>
                        <span>{{rFIComments.comment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{rFIComments.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.rfi')">Rfi</span>
                    </dt>
                    <dd>
                        <div v-if="rFIComments.rfiId">
                            <router-link :to="{name: 'RFIView', params: {rFIId: rFIComments.rfiId}}">{{rFIComments.rfiTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rFIComments.id" :to="{name: 'RFICommentsEdit', params: {rFICommentsId: rFIComments.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rfi-comments-details.component.ts">
</script>
