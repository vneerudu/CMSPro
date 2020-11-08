<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskLocations">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.detail.title')">TaskLocations</span> {{taskLocations.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{taskLocations.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{taskLocations.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskLocations.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{taskLocations.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskLocations.id" :to="{name: 'TaskLocationsEdit', params: {taskLocationsId: taskLocations.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-locations-details.component.ts">
</script>
