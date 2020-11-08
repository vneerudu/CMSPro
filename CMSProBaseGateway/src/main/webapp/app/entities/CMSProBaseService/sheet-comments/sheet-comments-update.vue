<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.home.createOrEditLabel')">Create or edit a SheetComments</h2>
                <div>
                    <div class="form-group" v-if="sheetComments.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sheetComments.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.createdBy')" for="sheet-comments-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="sheet-comments-createdBy"
                            :class="{'valid': !$v.sheetComments.createdBy.$invalid, 'invalid': $v.sheetComments.createdBy.$invalid }" v-model="$v.sheetComments.createdBy.$model"  required/>
                        <div v-if="$v.sheetComments.createdBy.$anyDirty && $v.sheetComments.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetComments.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.comment')" for="sheet-comments-comment">Comment</label>
                        <input type="text" class="form-control" name="comment" id="sheet-comments-comment"
                            :class="{'valid': !$v.sheetComments.comment.$invalid, 'invalid': $v.sheetComments.comment.$invalid }" v-model="$v.sheetComments.comment.$model"  required/>
                        <div v-if="$v.sheetComments.comment.$anyDirty && $v.sheetComments.comment.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetComments.comment.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.creationDate')" for="sheet-comments-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="sheet-comments-creationDate"
                                    v-model="$v.sheetComments.creationDate.$model"
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
                            <b-form-input id="sheet-comments-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.sheetComments.creationDate.$invalid, 'invalid': $v.sheetComments.creationDate.$invalid }"
                            v-model="$v.sheetComments.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.sheetComments.creationDate.$anyDirty && $v.sheetComments.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetComments.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetComments.sheet')" for="sheet-comments-sheet">Sheet</label>
                        <select class="form-control" id="sheet-comments-sheet" name="sheet" v-model="sheetComments.sheetId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="sheetsOption.id" v-for="sheetsOption in sheets" :key="sheetsOption.id">{{sheetsOption.number}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sheetComments.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sheet-comments-update.component.ts">
</script>
