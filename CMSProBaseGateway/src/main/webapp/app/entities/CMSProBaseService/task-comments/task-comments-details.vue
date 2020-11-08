<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="taskComments">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.detail.title')">TaskComments</span> {{taskComments.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{taskComments.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.comment')">Comment</span>
                    </dt>
                    <dd>
                        <span>{{taskComments.comment}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{taskComments.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTaskComments.task')">Task</span>
                    </dt>
                    <dd>
                        <div v-if="taskComments.taskId">
                            <router-link :to="{name: 'TasksView', params: {tasksId: taskComments.taskId}}">{{taskComments.taskTitle}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="taskComments.id" :to="{name: 'TaskCommentsEdit', params: {taskCommentsId: taskComments.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./task-comments-details.component.ts">
</script>
