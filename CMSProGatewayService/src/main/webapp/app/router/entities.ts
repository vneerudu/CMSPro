import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Project = () => import('@/entities/CMSProMicroService/project/project.vue');
// prettier-ignore
const ProjectUpdate = () => import('@/entities/CMSProMicroService/project/project-update.vue');
// prettier-ignore
const ProjectDetails = () => import('@/entities/CMSProMicroService/project/project-details.vue');
// prettier-ignore
const ProjectStatuses = () => import('@/entities/CMSProMicroService/project-statuses/project-statuses.vue');
// prettier-ignore
const ProjectStatusesUpdate = () => import('@/entities/CMSProMicroService/project-statuses/project-statuses-update.vue');
// prettier-ignore
const ProjectStatusesDetails = () => import('@/entities/CMSProMicroService/project-statuses/project-statuses-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/project',
    name: 'Project',
    component: Project,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/new',
    name: 'ProjectCreate',
    component: ProjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/:projectId/edit',
    name: 'ProjectEdit',
    component: ProjectUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project/:projectId/view',
    name: 'ProjectView',
    component: ProjectDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/project-statuses',
    name: 'ProjectStatuses',
    component: ProjectStatuses,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-statuses/new',
    name: 'ProjectStatusesCreate',
    component: ProjectStatusesUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-statuses/:projectStatusesId/edit',
    name: 'ProjectStatusesEdit',
    component: ProjectStatusesUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-statuses/:projectStatusesId/view',
    name: 'ProjectStatusesView',
    component: ProjectStatusesDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
