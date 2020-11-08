import { IUsers } from '@/shared/model/CMSProBaseService/users.model';
import { ITaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';
import { ITaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';
import { ITaskComments } from '@/shared/model/CMSProBaseService/task-comments.model';

export interface ITasks {
  id?: string;
  title?: string;
  startDate?: Date;
  dueDate?: Date;
  description?: string;
  costImpact?: boolean;
  costImpactComment?: string;
  scheduleImpact?: boolean;
  scheduleImpactComment?: string;
  createdBy?: string;
  creationDate?: Date;
  typeDescription?: string;
  typeId?: string;
  statusDescription?: string;
  statusId?: string;
  locationDescription?: string;
  locationId?: string;
  stampTitle?: string;
  stampId?: string;
  listDescription?: string;
  listId?: string;
  sheetNumber?: string;
  sheetId?: string;
  rootCausesDescription?: string;
  rootCausesId?: string;
  assignedTos?: IUsers[];
  monitors?: IUsers[];
  images?: ITaskAttachmentImages[];
  othersAttachments?: ITaskAttachmentOthers[];
  taskComments?: ITaskComments[];
  projectName?: string;
  projectId?: string;
}

export class Tasks implements ITasks {
  constructor(
    public id?: string,
    public title?: string,
    public startDate?: Date,
    public dueDate?: Date,
    public description?: string,
    public costImpact?: boolean,
    public costImpactComment?: string,
    public scheduleImpact?: boolean,
    public scheduleImpactComment?: string,
    public createdBy?: string,
    public creationDate?: Date,
    public typeDescription?: string,
    public typeId?: string,
    public statusDescription?: string,
    public statusId?: string,
    public locationDescription?: string,
    public locationId?: string,
    public stampTitle?: string,
    public stampId?: string,
    public listDescription?: string,
    public listId?: string,
    public sheetNumber?: string,
    public sheetId?: string,
    public rootCausesDescription?: string,
    public rootCausesId?: string,
    public assignedTos?: IUsers[],
    public monitors?: IUsers[],
    public images?: ITaskAttachmentImages[],
    public othersAttachments?: ITaskAttachmentOthers[],
    public taskComments?: ITaskComments[],
    public projectName?: string,
    public projectId?: string
  ) {
    this.costImpact = this.costImpact || false;
    this.scheduleImpact = this.scheduleImpact || false;
  }
}
