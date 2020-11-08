<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.createOrEditLabel" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.home.createOrEditLabel')">Create or edit a Addresses</h2>
                <div>
                    <div class="form-group" v-if="addresses.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="addresses.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address1')" for="addresses-address1">Address 1</label>
                        <input type="text" class="form-control" name="address1" id="addresses-address1"
                            :class="{'valid': !$v.addresses.address1.$invalid, 'invalid': $v.addresses.address1.$invalid }" v-model="$v.addresses.address1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address2')" for="addresses-address2">Address 2</label>
                        <input type="text" class="form-control" name="address2" id="addresses-address2"
                            :class="{'valid': !$v.addresses.address2.$invalid, 'invalid': $v.addresses.address2.$invalid }" v-model="$v.addresses.address2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.city')" for="addresses-city">City</label>
                        <input type="text" class="form-control" name="city" id="addresses-city"
                            :class="{'valid': !$v.addresses.city.$invalid, 'invalid': $v.addresses.city.$invalid }" v-model="$v.addresses.city.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.zipCode')" for="addresses-zipCode">Zip Code</label>
                        <input type="number" class="form-control" name="zipCode" id="addresses-zipCode"
                            :class="{'valid': !$v.addresses.zipCode.$invalid, 'invalid': $v.addresses.zipCode.$invalid }" v-model.number="$v.addresses.zipCode.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.isActive')" for="addresses-isActive">Is Active</label>
                        <input type="checkbox" class="form-check" name="isActive" id="addresses-isActive"
                            :class="{'valid': !$v.addresses.isActive.$invalid, 'invalid': $v.addresses.isActive.$invalid }" v-model="$v.addresses.isActive.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.createdBy')" for="addresses-createdBy">Created By</label>
                        <input type="text" class="form-control" name="createdBy" id="addresses-createdBy"
                            :class="{'valid': !$v.addresses.createdBy.$invalid, 'invalid': $v.addresses.createdBy.$invalid }" v-model="$v.addresses.createdBy.$model"  required/>
                        <div v-if="$v.addresses.createdBy.$anyDirty && $v.addresses.createdBy.$invalid">
                            <small class="form-text text-danger" v-if="!$v.addresses.createdBy.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.creationDate')" for="addresses-creationDate">Creation Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="addresses-creationDate"
                                    v-model="$v.addresses.creationDate.$model"
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
                            <b-form-input id="addresses-creationDate" type="text" class="form-control" name="creationDate"  :class="{'valid': !$v.addresses.creationDate.$invalid, 'invalid': $v.addresses.creationDate.$invalid }"
                            v-model="$v.addresses.creationDate.$model"  required />
                        </b-input-group>
                        <div v-if="$v.addresses.creationDate.$anyDirty && $v.addresses.creationDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.addresses.creationDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.addressType')" for="addresses-addressType">Address Type</label>
                        <select class="form-control" id="addresses-addressType" name="addressType" v-model="addresses.addressTypeId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="addressTypesOption.id" v-for="addressTypesOption in addressTypes" :key="addressTypesOption.id">{{addressTypesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.state')" for="addresses-state">State</label>
                        <select class="form-control" id="addresses-state" name="state" v-model="addresses.stateId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="statesOption.id" v-for="statesOption in states" :key="statesOption.id">{{statesOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.country')" for="addresses-country">Country</label>
                        <select class="form-control" id="addresses-country" name="country" v-model="addresses.countryId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="countryOption.id" v-for="countryOption in countries" :key="countryOption.id">{{countryOption.description}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.user')" for="addresses-user">User</label>
                        <select class="form-control" id="addresses-user" name="user" v-model="addresses.userId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="usersOption.id" v-for="usersOption in users" :key="usersOption.id">{{usersOption.fullName}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.project')" for="addresses-project">Project</label>
                        <select class="form-control" id="addresses-project" name="project" v-model="addresses.projectId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="projectsOption.id" v-for="projectsOption in projects" :key="projectsOption.id">{{projectsOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.addresses.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./addresses-update.component.ts">
</script>
