<template>
    <b-navbar toggleable="md" type="dark" class="bg-dark">
        <b-navbar-brand class="logo" b-link to="/">
            <span class="logo-img"></span>
            <span v-text="$t('global.title')" class="navbar-title">CMSProBaseGateway</span> <span class="navbar-version">{{version}}</span>
        </b-navbar-brand>      
        <b-navbar-toggle 
        right 
        class="jh-navbar-toggler d-lg-none" 
        href="javascript:void(0);"  
        data-toggle="collapse" 
        target="header-tabs" 
        aria-expanded="false" 
        aria-label="Toggle navigation">
            <font-awesome-icon icon="bars" />
        </b-navbar-toggle>
           
        <b-collapse is-nav id="header-tabs">
            <b-navbar-nav class="ml-auto">
                <b-nav-item to="/" exact>
                    <span>
                        <font-awesome-icon icon="home" />
                        <span v-text="$t('global.menu.home')">Home</span>
                    </span>
                </b-nav-item>
                <b-nav-item-dropdown
                    right
                    id="entity-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="th-list" />
                        <span v-text="$t('global.menu.entities.main')">Entities</span>
                    </span>
                    <b-dropdown-item to="/languages">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceLanguages')">Languages</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/logos">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceLogos')">Logos</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/account-statuses">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAccountStatuses')">AccountStatuses</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/accounts">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAccounts')">Accounts</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/user-groups">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceUserGroups')">UserGroups</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/users">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceUsers')">Users</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/address-types">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAddressTypes')">AddressTypes</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/addresses">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAddresses')">Addresses</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/states">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceStates')">States</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/country">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceCountry')">Country</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/user-roles">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceUserRoles')">UserRoles</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/user-permissions">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceUserPermissions')">UserPermissions</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/menu-items">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceMenuItems')">MenuItems</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/projects">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceProjects')">Projects</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sheet-tags">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceSheetTags')">SheetTags</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sheets">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceSheets')">Sheets</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sheet-history">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceSheetHistory')">SheetHistory</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/sheet-comments">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceSheetComments')">SheetComments</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/documents">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceDocuments')">Documents</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/attachments">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAttachments')">Attachments</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/attachment-images">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAttachmentImages')">AttachmentImages</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/attachment-others">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceAttachmentOthers')">AttachmentOthers</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/project-teams">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceProjectTeams')">ProjectTeams</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-statuses">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskStatuses')">TaskStatuses</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-types">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskTypes')">TaskTypes</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/lists">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceLists')">Lists</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/stamps">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceStamps')">Stamps</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/root-cause-groups">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRootCauseGroups')">RootCauseGroups</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/root-causes">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRootCauses')">RootCauses</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-attachments">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskAttachments')">TaskAttachments</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-attachment-images">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskAttachmentImages')">TaskAttachmentImages</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-attachment-others">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskAttachmentOthers')">TaskAttachmentOthers</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-comments">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskComments')">TaskComments</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/tasks">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTasks')">Tasks</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/task-locations">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceTaskLocations')">TaskLocations</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/rfi-statuses">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRfiStatuses')">RFIStatuses</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/rfi">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRfi')">RFI</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/rfi-comments">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRfiComments')">RFIComments</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/rfi-time-line">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.cmsProBaseServiceRfiTimeLine')">RFITimeLine</span>
                    </b-dropdown-item>
                    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    right
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="cogs" />
                        <span v-text="$t('global.menu.admin.main')">Administration</span>
                    </span>
                    <b-dropdown-item to="/admin/gateway" active-class="active">
                        <font-awesome-icon icon="road" />
                        <span v-text="$t('global.menu.admin.gateway')">Gateway</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/user-management" active-class="active">
                        <font-awesome-icon icon="user" />
                        <span v-text="$t('global.menu.admin.userManagement')">User management</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-tracker" active-class="active">
                        <font-awesome-icon icon="eye" />
                        <span v-text="$t('global.menu.admin.tracker')">User tracker</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-metrics" active-class="active">
                        <font-awesome-icon icon="tachometer-alt" />
                        <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-health" active-class="active">
                        <font-awesome-icon icon="heart" />
                        <span v-text="$t('global.menu.admin.health')">Health</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-configuration" active-class="active">
                        <font-awesome-icon icon="list" />
                        <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/audits" active-class="active">
                        <font-awesome-icon icon="bell" />
                        <span v-text="$t('global.menu.admin.audits')">Audits</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/logs" active-class="active">
                        <font-awesome-icon icon="tasks" />
                        <span v-text="$t('global.menu.admin.logs')">Logs</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs" active-class="active">
                        <font-awesome-icon icon="book" />
                        <span v-text="$t('global.menu.admin.apidocs')">API</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
                    <span slot="button-content">
                        <font-awesome-icon icon="flag" />
                        <span v-text="$t('global.menu.language')">Language</span>
                    </span>
                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
                        :class="{ active: isActiveLanguage(key)}">
                        {{value.name}}
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    right
                    href="javascript:void(0);"
                    id="account-menu"
                    :class="{'router-link-active': subIsActive('/account')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user" />
                        <span v-text="$t('global.menu.account.main')">
                            Account
                        </span>
                    </span>
                    <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                        <font-awesome-icon icon="wrench" />
                        <span v-text="$t('global.menu.account.settings')">Settings</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
                        <font-awesome-icon icon="lock" />
                        <span v-text="$t('global.menu.account.password')">Password</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout" active-class="active">
                        <font-awesome-icon icon="sign-out-alt" />
                        <span v-text="$t('global.menu.account.logout')">Sign out</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login" active-class="active">
                        <font-awesome-icon icon="sign-in-alt" />
                        <span v-text="$t('global.menu.account.login')">Sign in</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated" active-class="active">
                        <font-awesome-icon icon="user-plus" />
                        <span v-text="$t('global.menu.account.register')">Register</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}


@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span{
    display:none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url("../../../content/images/logo-jhipster.png") no-repeat center
    center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
