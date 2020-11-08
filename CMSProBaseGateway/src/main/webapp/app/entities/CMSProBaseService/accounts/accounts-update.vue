<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.home.createOrEditLabel')">Create or edit a Accounts</h2>
                <div>
                    <div class="form-group" v-if="accounts.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="accounts.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.accountNumber')" for="accounts-accountNumber">Account Number</label>
                        <input type="number" class="form-control" name="accountNumber" id="accounts-accountNumber"
                            :class="{'valid': !$v.accounts.accountNumber.$invalid, 'invalid': $v.accounts.accountNumber.$invalid }" v-model.number="$v.accounts.accountNumber.$model"  required/>
                        <div v-if="$v.accounts.accountNumber.$anyDirty && $v.accounts.accountNumber.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.accountNumber.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.accounts.accountNumber.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.firstName')" for="accounts-firstName">First Name</label>
                        <input type="text" class="form-control" name="firstName" id="accounts-firstName"
                            :class="{'valid': !$v.accounts.firstName.$invalid, 'invalid': $v.accounts.firstName.$invalid }" v-model="$v.accounts.firstName.$model"  required/>
                        <div v-if="$v.accounts.firstName.$anyDirty && $v.accounts.firstName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.firstName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.lastName')" for="accounts-lastName">Last Name</label>
                        <input type="text" class="form-control" name="lastName" id="accounts-lastName"
                            :class="{'valid': !$v.accounts.lastName.$invalid, 'invalid': $v.accounts.lastName.$invalid }" v-model="$v.accounts.lastName.$model"  required/>
                        <div v-if="$v.accounts.lastName.$anyDirty && $v.accounts.lastName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.lastName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.emailAddress')" for="accounts-emailAddress">Email Address</label>
                        <input type="text" class="form-control" name="emailAddress" id="accounts-emailAddress"
                            :class="{'valid': !$v.accounts.emailAddress.$invalid, 'invalid': $v.accounts.emailAddress.$invalid }" v-model="$v.accounts.emailAddress.$model"  required/>
                        <div v-if="$v.accounts.emailAddress.$anyDirty && $v.accounts.emailAddress.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.emailAddress.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.phoneNumber')" for="accounts-phoneNumber">Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" id="accounts-phoneNumber"
                            :class="{'valid': !$v.accounts.phoneNumber.$invalid, 'invalid': $v.accounts.phoneNumber.$invalid }" v-model="$v.accounts.phoneNumber.$model"  required/>
                        <div v-if="$v.accounts.phoneNumber.$anyDirty && $v.accounts.phoneNumber.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.phoneNumber.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.creationDate')" for="accounts-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="accounts-creationDate"
                                    v-model="$v.accounts.creationDate.$model"
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
                            <b-form-input id="accounts-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.accounts.creationDate.$invalid, 'invalid': $v.accounts.creationDate.$invalid }"
                            v-model="$v.accounts.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.accounts.creationDate.$anyDirty && $v.accounts.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.createdBy')" for="accounts-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="accounts-createdBy"
                            :class="{'valid': !$v.accounts.createdBy.$invalid, 'invalid': $v.accounts.createdBy.$invalid }" v-model="$v.accounts.createdBy.$model"  required/>
                        <div v-if="$v.accounts.createdBy.$anyDirty && $v.accounts.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.accounts.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.language')" for="accounts-language">Language</label>
                        <select class="form-control" id="accounts-language" name="language" v-model="accounts.languageId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="languagesOption.id" v-for="languagesOption in languages" :key="languagesOption.id">{{languagesOption.code}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.logo')" for="accounts-logo">Logo</label>
                        <select class="form-control" id="accounts-logo" name="logo" v-model="accounts.logoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="logosOption.id" v-for="logosOption in logos" :key="logosOption.id">{{logosOption.fileName}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.status')" for="accounts-status">Status</label>
                        <select class="form-control" id="accounts-status" name="status" v-model="accounts.statusId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="accountStatusesOption.id" v-for="accountStatusesOption in accountStatuses" :key="accountStatusesOption.id">{{accountStatusesOption.description}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.accounts.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./accounts-update.component.ts">
</script>
