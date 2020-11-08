<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.home.createOrEditLabel')">Create or edit a RFIComments</h2>
                <div>
                    <div class="form-group" v-if="rFIComments.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rFIComments.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.createdBy')" for="rfi-comments-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="rfi-comments-createdBy"
                            :class="{'valid': !$v.rFIComments.createdBy.$invalid, 'invalid': $v.rFIComments.createdBy.$invalid }" v-model="$v.rFIComments.createdBy.$model"  required/>
                        <div v-if="$v.rFIComments.createdBy.$anyDirty && $v.rFIComments.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFIComments.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.comment')" for="rfi-comments-comment">Comment</label>
                        <input type="text" class="form-control" name="comment" id="rfi-comments-comment"
                            :class="{'valid': !$v.rFIComments.comment.$invalid, 'invalid': $v.rFIComments.comment.$invalid }" v-model="$v.rFIComments.comment.$model"  required/>
                        <div v-if="$v.rFIComments.comment.$anyDirty && $v.rFIComments.comment.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFIComments.comment.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.creationDate')" for="rfi-comments-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="rfi-comments-creationDate"
                                    v-model="$v.rFIComments.creationDate.$model"
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
                            <b-form-input id="rfi-comments-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.rFIComments.creationDate.$invalid, 'invalid': $v.rFIComments.creationDate.$invalid }"
                            v-model="$v.rFIComments.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.rFIComments.creationDate.$anyDirty && $v.rFIComments.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.rFIComments.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceRFiComments.rfi')" for="rfi-comments-rfi">Rfi</label>
                        <select class="form-control" id="rfi-comments-rfi" name="rfi" v-model="rFIComments.rfiId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="rFIOption.id" v-for="rFIOption in rFIS" :key="rFIOption.id">{{rFIOption.title}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.rFIComments.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rfi-comments-update.component.ts">
</script>
