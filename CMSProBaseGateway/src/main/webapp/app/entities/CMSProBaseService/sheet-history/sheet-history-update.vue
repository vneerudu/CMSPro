<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.home.createOrEditLabel')">Create or edit a SheetHistory</h2>
                <div>
                    <div class="form-group" v-if="sheetHistory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sheetHistory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.number')" for="sheet-history-number">Number</label>
                        <input type="number" class="form-control" name="number" id="sheet-history-number"
                            :class="{'valid': !$v.sheetHistory.number.$invalid, 'invalid': $v.sheetHistory.number.$invalid }" v-model.number="$v.sheetHistory.number.$model"  required/>
                        <div v-if="$v.sheetHistory.number.$anyDirty && $v.sheetHistory.number.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetHistory.number.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.sheetHistory.number.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.version')" for="sheet-history-version">Version</label>
                        <input type="text" class="form-control" name="version" id="sheet-history-version"
                            :class="{'valid': !$v.sheetHistory.version.$invalid, 'invalid': $v.sheetHistory.version.$invalid }" v-model="$v.sheetHistory.version.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.isActive')" for="sheet-history-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="sheet-history-isActive"
                            :class="{'valid': !$v.sheetHistory.isActive.$invalid, 'invalid': $v.sheetHistory.isActive.$invalid }" v-model="$v.sheetHistory.isActive.$model"  required/>
                        <div v-if="$v.sheetHistory.isActive.$anyDirty && $v.sheetHistory.isActive.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetHistory.isActive.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.createdBy')" for="sheet-history-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="sheet-history-createdBy"
                            :class="{'valid': !$v.sheetHistory.createdBy.$invalid, 'invalid': $v.sheetHistory.createdBy.$invalid }" v-model="$v.sheetHistory.createdBy.$model"  required/>
                        <div v-if="$v.sheetHistory.createdBy.$anyDirty && $v.sheetHistory.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetHistory.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.creationDate')" for="sheet-history-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="sheet-history-creationDate"
                                    v-model="$v.sheetHistory.creationDate.$model"
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
                            <b-form-input id="sheet-history-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.sheetHistory.creationDate.$invalid, 'invalid': $v.sheetHistory.creationDate.$invalid }"
                            v-model="$v.sheetHistory.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.sheetHistory.creationDate.$anyDirty && $v.sheetHistory.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetHistory.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetHistory.sheet')" for="sheet-history-sheet">Sheet</label>
                        <select class="form-control" id="sheet-history-sheet" name="sheet" v-model="sheetHistory.sheetId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="sheetsOption.id" v-for="sheetsOption in sheets" :key="sheetsOption.id">{{sheetsOption.number}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sheetHistory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sheet-history-update.component.ts">
</script>
