<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="tasks">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.detail.title')">Tasks</span> {{tasks.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{tasks.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{tasks.startDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.dueDate')">Due Date</span>
                    </dt>
                    <dd>
                        <span>{{tasks.dueDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{tasks.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpact')">Cost Impact</span>
                    </dt>
                    <dd>
                        <span>{{tasks.costImpact}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpactComment')">Cost Impact Comment</span>
                    </dt>
                    <dd>
                        <span>{{tasks.costImpactComment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpact')">Schedule Impact</span>
                    </dt>
                    <dd>
                        <span>{{tasks.scheduleImpact}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpactComment')">Schedule Impact Comment</span>
                    </dt>
                    <dd>
                        <span>{{tasks.scheduleImpactComment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{tasks.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{tasks.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.type')">Type</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.typeId">
                            <router-link :to="{name: 'TaskTypesView', params: {taskTypesId: tasks.typeId}}">{{tasks.typeDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.status')">Status</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.statusId">
                            <router-link :to="{name: 'TaskStatusesView', params: {taskStatusesId: tasks.statusId}}">{{tasks.statusDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.location')">Location</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.locationId">
                            <router-link :to="{name: 'TaskLocationsView', params: {taskLocationsId: tasks.locationId}}">{{tasks.locationDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.stamp')">Stamp</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.stampId">
                            <router-link :to="{name: 'StampsView', params: {stampsId: tasks.stampId}}">{{tasks.stampTitle}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.list')">List</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.listId">
                            <router-link :to="{name: 'ListsView', params: {listsId: tasks.listId}}">{{tasks.listDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.sheet')">Sheet</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.sheetId">
                            <router-link :to="{name: 'SheetsView', params: {sheetsId: tasks.sheetId}}">{{tasks.sheetNumber}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.rootCauses')">Root Causes</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.rootCausesId">
                            <router-link :to="{name: 'RootCausesView', params: {rootCausesId: tasks.rootCausesId}}">{{tasks.rootCausesDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="tasks.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: tasks.projectId}}">{{tasks.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="tasks.id" :to="{name: 'TasksEdit', params: {tasksId: tasks.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tasks-details.component.ts">
</script>
