<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskStatuses">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.detail.title')">TaskStatuses</span> {{taskStatuses.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{taskStatuses.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{taskStatuses.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskStatuses.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{taskStatuses.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskStatuses.id" :to="{name: 'TaskStatusesEdit', params: {taskStatusesId: taskStatuses.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-statuses-details.component.ts">
</script>
