<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.home.createOrEditLabel')">Create or edit a SheetTags</h2>
                <div>
                    <div class="form-group" v-if="sheetTags.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="sheetTags.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.code')" for="sheet-tags-code">Code</label>
                        <input type="text" class="form-control" name="code" id="sheet-tags-code"
                            :class="{'valid': !$v.sheetTags.code.$invalid, 'invalid': $v.sheetTags.code.$invalid }" v-model="$v.sheetTags.code.$model"  required/>
                        <div v-if="$v.sheetTags.code.$anyDirty && $v.sheetTags.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetTags.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.description')" for="sheet-tags-description">Description</label>
                        <input type="text" class="form-control" name="description" id="sheet-tags-description"
                            :class="{'valid': !$v.sheetTags.description.$invalid, 'invalid': $v.sheetTags.description.$invalid }" v-model="$v.sheetTags.description.$model"  required/>
                        <div v-if="$v.sheetTags.description.$anyDirty && $v.sheetTags.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.sheetTags.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.isActive')" for="sheet-tags-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="sheet-tags-isActive"
                            :class="{'valid': !$v.sheetTags.isActive.$invalid, 'invalid': $v.sheetTags.isActive.$invalid }" v-model="$v.sheetTags.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceSheetTags.sheets')" for="sheet-tags-sheets">Sheets</label>
                        <select class="form-control" id="sheet-tags-sheets" name="sheets" v-model="sheetTags.sheetsId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="sheetsOption.id" v-for="sheetsOption in sheets" :key="sheetsOption.id">{{sheetsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.sheetTags.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./sheet-tags-update.component.ts">
</script>
