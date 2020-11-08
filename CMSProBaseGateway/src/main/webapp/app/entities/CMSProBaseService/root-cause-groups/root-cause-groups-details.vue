<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rootCauseGroups">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.detail.title')">RootCauseGroups</span> {{rootCauseGroups.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{rootCauseGroups.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{rootCauseGroups.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauseGroups.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{rootCauseGroups.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rootCauseGroups.id" :to="{name: 'RootCauseGroupsEdit', params: {rootCauseGroupsId: rootCauseGroups.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./root-cause-groups-details.component.ts">
</script>
