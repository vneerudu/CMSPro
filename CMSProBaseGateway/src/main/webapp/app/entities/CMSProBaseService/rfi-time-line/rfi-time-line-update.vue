<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.home.createOrEditLabel')">Create or edit a RFITimeLine</h2>
                <div>
                    <div class="form-group" v-if="rFITimeLine.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rFITimeLine.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.createdBy')" for="rfi-time-line-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="rfi-time-line-createdBy"
                            :class="{'valid': !$v.rFITimeLine.createdBy.$invalid, 'invalid': $v.rFITimeLine.createdBy.$invalid }" v-model="$v.rFITimeLine.createdBy.$model"  required/>
                        <div v-if="$v.rFITimeLine.createdBy.$anyDirty && $v.rFITimeLine.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFITimeLine.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.message')" for="rfi-time-line-message">Message</label>
                        <input type="text" class="form-control" name="message" id="rfi-time-line-message"
                            :class="{'valid': !$v.rFITimeLine.message.$invalid, 'invalid': $v.rFITimeLine.message.$invalid }" v-model="$v.rFITimeLine.message.$model"  required/>
                        <div v-if="$v.rFITimeLine.message.$anyDirty && $v.rFITimeLine.message.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFITimeLine.message.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.creationDate')" for="rfi-time-line-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="rfi-time-line-creationDate"
                                    v-model="$v.rFITimeLine.creationDate.$model"
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
                            <b-form-input id="rfi-time-line-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.rFITimeLine.creationDate.$invalid, 'invalid': $v.rFITimeLine.creationDate.$invalid }"
                            v-model="$v.rFITimeLine.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.rFITimeLine.creationDate.$anyDirty && $v.rFITimeLine.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFITimeLine.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiTimeLine.rfi')" for="rfi-time-line-rfi">Rfi</label>
                        <select class="form-control" id="rfi-time-line-rfi" name="rfi" v-model="rFITimeLine.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rFITimeLine.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rfi-time-line-update.component.ts">
</script>
