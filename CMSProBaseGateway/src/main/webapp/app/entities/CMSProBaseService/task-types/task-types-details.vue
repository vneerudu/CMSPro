<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskTypes">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.detail.title')">TaskTypes</span> {{taskTypes.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{taskTypes.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{taskTypes.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskTypes.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{taskTypes.isActive}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskTypes.id" :to="{name: 'TaskTypesEdit', params: {taskTypesId: taskTypes.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-types-details.component.ts">
</script>
