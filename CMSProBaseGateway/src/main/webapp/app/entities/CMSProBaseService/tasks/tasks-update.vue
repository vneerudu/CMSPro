<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.home.createOrEditLabel')">Create or edit a Tasks</h2>
                <div>
                    <div class="form-group" v-if="tasks.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="tasks.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.title')" for="tasks-title">Title</label>
                        <input type="text" class="form-control" name="title" id="tasks-title"
                            :class="{'valid': !$v.tasks.title.$invalid, 'invalid': $v.tasks.title.$invalid }" v-model="$v.tasks.title.$model"  required/>
                        <div v-if="$v.tasks.title.$anyDirty && $v.tasks.title.$invalid">
                            <small class="form-text text-danger" v-if="!$v.tasks.title.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.startDate')" for="tasks-startDate">Start Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="tasks-startDate"
                                    v-model="$v.tasks.startDate.$model"
                                    name="startDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="tasks-startDate" type="text" class="form-control" name="startDate"  :class="{'valid': !$v.tasks.startDate.$invalid, 'invalid': $v.tasks.startDate.$invalid }"
                            v-model="$v.tasks.startDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.dueDate')" for="tasks-dueDate">Due Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="tasks-dueDate"
                                    v-model="$v.tasks.dueDate.$model"
                                    name="dueDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="tasks-dueDate" type="text" class="form-control" name="dueDate"  :class="{'valid': !$v.tasks.dueDate.$invalid, 'invalid': $v.tasks.dueDate.$invalid }"
                            v-model="$v.tasks.dueDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.description')" for="tasks-description">Description</label>
                        <input type="text" class="form-control" name="description" id="tasks-description"
                            :class="{'valid': !$v.tasks.description.$invalid, 'invalid': $v.tasks.description.$invalid }" v-model="$v.tasks.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpact')" for="tasks-costImpact">Cost Impact</label>
                        <input type="checkbox" class="form-check" name="costImpact" id="tasks-costImpact"
                            :class="{'valid': !$v.tasks.costImpact.$invalid, 'invalid': $v.tasks.costImpact.$invalid }" v-model="$v.tasks.costImpact.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.costImpactComment')" for="tasks-costImpactComment">Cost Impact Comment</label>
                        <input type="text" class="form-control" name="costImpactComment" id="tasks-costImpactComment"
                            :class="{'valid': !$v.tasks.costImpactComment.$invalid, 'invalid': $v.tasks.costImpactComment.$invalid }" v-model="$v.tasks.costImpactComment.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpact')" for="tasks-scheduleImpact">Schedule Impact</label>
                        <input type="checkbox" class="form-check" name="scheduleImpact" id="tasks-scheduleImpact"
                            :class="{'valid': !$v.tasks.scheduleImpact.$invalid, 'invalid': $v.tasks.scheduleImpact.$invalid }" v-model="$v.tasks.scheduleImpact.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.scheduleImpactComment')" for="tasks-scheduleImpactComment">Schedule Impact Comment</label>
                        <input type="text" class="form-control" name="scheduleImpactComment" id="tasks-scheduleImpactComment"
                            :class="{'valid': !$v.tasks.scheduleImpactComment.$invalid, 'invalid': $v.tasks.scheduleImpactComment.$invalid }" v-model="$v.tasks.scheduleImpactComment.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.createdBy')" for="tasks-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="tasks-createdBy"
                            :class="{'valid': !$v.tasks.createdBy.$invalid, 'invalid': $v.tasks.createdBy.$invalid }" v-model="$v.tasks.createdBy.$model"  required/>
                        <div v-if="$v.tasks.createdBy.$anyDirty && $v.tasks.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.tasks.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.creationDate')" for="tasks-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="tasks-creationDate"
                                    v-model="$v.tasks.creationDate.$model"
                                    name="creationDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="tasks-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.tasks.creationDate.$invalid, 'invalid': $v.tasks.creationDate.$invalid }"
                            v-model="$v.tasks.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.tasks.creationDate.$anyDirty && $v.tasks.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.tasks.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.type')" for="tasks-type">Type</label>
                        <select class="form-control" id="tasks-type" name="type" v-model="tasks.typeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="taskTypesOption.id" v-for="taskTypesOption in taskTypes" :key="taskTypesOption.id">{{taskTypesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.status')" for="tasks-status">Status</label>
                        <select class="form-control" id="tasks-status" name="status" v-model="tasks.statusId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="taskStatusesOption.id" v-for="taskStatusesOption in taskStatuses" :key="taskStatusesOption.id">{{taskStatusesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.location')" for="tasks-location">Location</label>
                        <select class="form-control" id="tasks-location" name="location" v-model="tasks.locationId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="taskLocationsOption.id" v-for="taskLocationsOption in taskLocations" :key="taskLocationsOption.id">{{taskLocationsOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.stamp')" for="tasks-stamp">Stamp</label>
                        <select class="form-control" id="tasks-stamp" name="stamp" v-model="tasks.stampId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stampsOption.id" v-for="stampsOption in stamps" :key="stampsOption.id">{{stampsOption.title}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.list')" for="tasks-list">List</label>
                        <select class="form-control" id="tasks-list" name="list" v-model="tasks.listId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="listsOption.id" v-for="listsOption in lists" :key="listsOption.id">{{listsOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.sheet')" for="tasks-sheet">Sheet</label>
                        <select class="form-control" id="tasks-sheet" name="sheet" v-model="tasks.sheetId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="sheetsOption.id" v-for="sheetsOption in sheets" :key="sheetsOption.id">{{sheetsOption.number}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.rootCauses')" for="tasks-rootCauses">Root Causes</label>
                        <select class="form-control" id="tasks-rootCauses" name="rootCauses" v-model="tasks.rootCausesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rootCausesOption.id" v-for="rootCausesOption in rootCauses" :key="rootCausesOption.id">{{rootCausesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceTasks.project')" for="tasks-project">Project</label>
                        <select class="form-control" id="tasks-project" name="project" v-model="tasks.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.tasks.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./tasks-update.component.ts">
</script>
