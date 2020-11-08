<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceLanguages.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLanguages.home.createOrEditLabel')">Create or edit a Languages</h2>
                <div>
                    <div class="form-group" v-if="languages.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="languages.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLanguages.code')" for="languages-code">Code</label>
                        <input type="text" class="form-control" name="code" id="languages-code"
                            :class="{'valid': !$v.languages.code.$invalid, 'invalid': $v.languages.code.$invalid }" v-model="$v.languages.code.$model"  required/>
                        <div v-if="$v.languages.code.$anyDirty && $v.languages.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.languages.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceLanguages.description')" for="languages-description">Description</label>
                        <input type="text" class="form-control" name="description" id="languages-description"
                            :class="{'valid': !$v.languages.description.$invalid, 'invalid': $v.languages.description.$invalid }" v-model="$v.languages.description.$model"  required/>
                        <div v-if="$v.languages.description.$anyDirty && $v.languages.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.languages.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.languages.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./languages-update.component.ts">
</script>
