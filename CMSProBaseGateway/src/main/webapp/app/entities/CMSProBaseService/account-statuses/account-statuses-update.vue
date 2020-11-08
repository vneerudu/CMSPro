<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.home.createOrEditLabel')">Create or edit a AccountStatuses</h2>
                <div>
                    <div class="form-group" v-if="accountStatuses.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="accountStatuses.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.code')" for="account-statuses-code">Code</label>
                        <input type="text" class="form-control" name="code" id="account-statuses-code"
                            :class="{'valid': !$v.accountStatuses.code.$invalid, 'invalid': $v.accountStatuses.code.$invalid }" v-model="$v.accountStatuses.code.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.description')" for="account-statuses-description">Description</label>
                        <input type="text" class="form-control" name="description" id="account-statuses-description"
                            :class="{'valid': !$v.accountStatuses.description.$invalid, 'invalid': $v.accountStatuses.description.$invalid }" v-model="$v.accountStatuses.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccountStatuses.isActive')" for="account-statuses-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="account-statuses-isActive"
                            :class="{'valid': !$v.accountStatuses.isActive.$invalid, 'invalid': $v.accountStatuses.isActive.$invalid }" v-model="$v.accountStatuses.isActive.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.accountStatuses.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./account-statuses-update.component.ts">
</script>
