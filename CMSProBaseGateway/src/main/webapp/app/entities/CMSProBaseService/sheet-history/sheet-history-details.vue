<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sheetHistory">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.detail.title')">SheetHistory</span> {{sheetHistory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{sheetHistory.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.version')">Version</span>
                    </dt>
                    <dd>
                        <span>{{sheetHistory.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{sheetHistory.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{sheetHistory.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{sheetHistory.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.sheet')">Sheet</span>
                    </dt>
                    <dd>
                        <div v-if="sheetHistory.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: sheetHistory.sheetId}}">{{sheetHistory.sheetNumber}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sheetHistory.id" :to="{name: 'SheetHistoryEdit', params: {sheetHistoryId: sheetHistory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheet-history-details.component.ts">
</script>
