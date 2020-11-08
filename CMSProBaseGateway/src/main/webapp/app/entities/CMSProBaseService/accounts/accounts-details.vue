<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="accounts">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.detail.title')">Accounts</span> {{accounts.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.accountNumber')">Account Number</span>
                    </dt>
                    <dd>
                        <span>{{accounts.accountNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.firstName')">First Name</span>
                    </dt>
                    <dd>
                        <span>{{accounts.firstName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.lastName')">Last Name</span>
                    </dt>
                    <dd>
                        <span>{{accounts.lastName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.emailAddress')">Email Address</span>
                    </dt>
                    <dd>
                        <span>{{accounts.emailAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.phoneNumber')">Phone Number</span>
                    </dt>
                    <dd>
                        <span>{{accounts.phoneNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.creationDate')">Creation Date</span>
                    </dt>
                    <dd>
                        <span>{{accounts.creationDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.createdBy')">Created By</span>
                    </dt>
                    <dd>
                        <span>{{accounts.createdBy}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.language')">Language</span>
                    </dt>
                    <dd>
                        <div v-if="accounts.languageId">
                            <router-link :to="{name: 'LanguagesView', params: {languagesId: accounts.languageId}}">{{accounts.languageCode}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.logo')">Logo</span>
                    </dt>
                    <dd>
                        <div v-if="accounts.logoId">
                            <router-link :to="{name: 'LogosView', params: {logosId: accounts.logoId}}">{{accounts.logoFileName}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAccounts.status')">Status</span>
                    </dt>
                    <dd>
                        <div v-if="accounts.statusId">
                            <router-link :to="{name: 'AccountStatusesView', params: {accountStatusesId: accounts.statusId}}">{{accounts.statusDescription}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="accounts.id" :to="{name: 'AccountsEdit', params: {accountsId: accounts.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./accounts-details.component.ts">
</script>
