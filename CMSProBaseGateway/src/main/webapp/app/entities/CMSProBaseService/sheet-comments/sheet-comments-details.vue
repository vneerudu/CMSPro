<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sheetComments">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.detail.title')">SheetComments</span> {{sheetComments.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{sheetComments.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.comment')">Comment</span>
                    </dt>
                    <dd>
                        <span>{{sheetComments.comment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{sheetComments.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.sheet')">Sheet</span>
                    </dt>
                    <dd>
                        <div v-if="sheetComments.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: sheetComments.sheetId}}">{{sheetComments.sheetNumber}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sheetComments.id" :to="{name: 'SheetCommentsEdit', params: {sheetCommentsId: sheetComments.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheet-comments-details.component.ts">
</script>
