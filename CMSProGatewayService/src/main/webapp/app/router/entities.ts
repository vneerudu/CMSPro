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
// prettier-ignore
const Projects = () => import('@/entities/CMSProMicroService/projects/projects.vue');
// prettier-ignore
const ProjectsUpdate = () => import('@/entities/CMSProMicroService/projects/projects-update.vue');
// prettier-ignore
const ProjectsDetails = () => import('@/entities/CMSProMicroService/projects/projects-details.vue');
// prettier-ignore
const ProjectStatus = () => import('@/entities/CMSProMicroService/project-status/project-status.vue');
// prettier-ignore
const ProjectStatusUpdate = () => import('@/entities/CMSProMicroService/project-status/project-status-update.vue');
// prettier-ignore
const ProjectStatusDetails = () => import('@/entities/CMSProMicroService/project-status/project-status-details.vue');
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

  {
    path: '/projects',
    name: 'Projects',
    component: Projects,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/projects/new',
    name: 'ProjectsCreate',
    component: ProjectsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/projects/:projectsId/edit',
    name: 'ProjectsEdit',
    component: ProjectsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/projects/:projectsId/view',
    name: 'ProjectsView',
    component: ProjectsDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/project-status',
    name: 'ProjectStatus',
    component: ProjectStatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-status/new',
    name: 'ProjectStatusCreate',
    component: ProjectStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-status/:projectStatusId/edit',
    name: 'ProjectStatusEdit',
    component: ProjectStatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/project-status/:projectStatusId/view',
    name: 'ProjectStatusView',
    component: ProjectStatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
