<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rootCauses">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.detail.title')">RootCauses</span> {{rootCauses.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{rootCauses.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{rootCauses.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{rootCauses.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRootCauses.groups')">Groups</span>
                    </dt>
                    <dd>
                        <div v-if="rootCauses.groupsId">
                            <router-link :to="{name: 'RootCauseGroupsView', params: {rootCauseGroupsId: rootCauses.groupsId}}">{{rootCauses.groupsCode}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rootCauses.id" :to="{name: 'RootCausesEdit', params: {rootCausesId: rootCauses.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./root-causes-details.component.ts">
</script>
