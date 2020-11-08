<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceCountry.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceCountry.home.createOrEditLabel')">Create or edit a Country</h2>
                <div>
                    <div class="form-group" v-if="country.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="country.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceCountry.code')" for="country-code">Code</label>
                        <input type="text" class="form-control" name="code" id="country-code"
                            :class="{'valid': !$v.country.code.$invalid, 'invalid': $v.country.code.$invalid }" v-model="$v.country.code.$model"  required/>
                        <div v-if="$v.country.code.$anyDirty && $v.country.code.$invalid">
                            <small class="form-text text-danger" v-if="!$v.country.code.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceCountry.description')" for="country-description">Description</label>
                        <input type="text" class="form-control" name="description" id="country-description"
                            :class="{'valid': !$v.country.description.$invalid, 'invalid': $v.country.description.$invalid }" v-model="$v.country.description.$model"  required/>
                        <div v-if="$v.country.description.$anyDirty && $v.country.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.country.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.country.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./country-update.component.ts">
</script>
