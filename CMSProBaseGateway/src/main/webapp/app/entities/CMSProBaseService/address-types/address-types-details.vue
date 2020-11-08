<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="addressTypes">
                <h2 class="jh-entity-heading"><span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddressTypes.detail.title')">AddressTypes</span> {{addressTypes.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddressTypes.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{addressTypes.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('cmsProBaseGatewayApp.cmsProBaseServiceAddressTypes.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{addressTypes.description}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="addressTypes.id" :to="{name: 'AddressTypesEdit', params: {addressTypesId: addressTypes.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./address-types-details.component.ts">
</script>
