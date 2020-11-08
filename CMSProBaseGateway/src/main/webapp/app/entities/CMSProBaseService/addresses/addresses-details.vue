<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="addresses">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.detail.title')">Addresses</span> {{addresses.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address1')">Address 1</span>
                    </dt>
                    <dd>
                        <span>{{addresses.address1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.address2')">Address 2</span>
                    </dt>
                    <dd>
                        <span>{{addresses.address2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.city')">City</span>
                    </dt>
                    <dd>
                        <span>{{addresses.city}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.zipCode')">Zip Code</span>
                    </dt>
                    <dd>
                        <span>{{addresses.zipCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.isActive')">Is Active</span>
                    </dt>
                    <dd>
                        <span>{{addresses.isActive}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{addresses.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{addresses.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.addressType')">Address Type</span>
                    </dt>
                    <dd>
                        <div v-if="addresses.addressTypeId">
                            <router-link :to="{name: 'AddressTypesView', params: {addressTypesId: addresses.addressTypeId}}">{{addresses.addressTypeDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.state')">State</span>
                    </dt>
                    <dd>
                        <div v-if="addresses.stateId">
                            <router-link :to="{name: 'StatesView', params: {statesId: addresses.stateId}}">{{addresses.stateDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="addresses.countryId">
                            <router-link :to="{name: 'CountryView', params: {countryId: addresses.countryId}}">{{addresses.countryDescription}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.user')">User</span>
                    </dt>
                    <dd>
                        <div v-if="addresses.userId">
                            <router-link :to="{name: 'UsersView', params: {usersId: addresses.userId}}">{{addresses.userFullName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddresses.project')">Project</span>
                    </dt>
                    <dd>
                        <div v-if="addresses.projectId">
                            <router-link :to="{name: 'ProjectsView', params: {projectsId: addresses.projectId}}">{{addresses.projectName}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="addresses.id" :to="{name: 'AddressesEdit', params: {addressesId: addresses.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./addresses-details.component.ts">
</script>
