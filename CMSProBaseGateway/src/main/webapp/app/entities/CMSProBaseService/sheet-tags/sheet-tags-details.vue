<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="sheetTags">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.detail.title')">SheetTags</span> {{sheetTags.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{sheetTags.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{sheetTags.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{sheetTags.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.sheets')">Sheets</span>
                    </dt>
                    <dd>
                        <div v-if="sheetTags.sheetsId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: sheetTags.sheetsId}}">{{sheetTags.sheetsId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="sheetTags.id" :to="{name: 'SheetTagsEdit', params: {sheetTagsId: sheetTags.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./sheet-tags-details.component.ts">
</script>
