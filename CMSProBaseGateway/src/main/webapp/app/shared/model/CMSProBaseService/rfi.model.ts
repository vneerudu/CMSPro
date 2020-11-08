import { ITaskAttachmentImages } from '@/shared/model/CMSProBaseService/task-attachment-images.model';
import { ITaskAttachmentOthers } from '@/shared/model/CMSProBaseService/task-attachment-others.model';
import { IAttachmentOthers } from '@/shared/model/CMSProBaseService/attachment-others.model';
import { IAttachmentImages } from '@/shared/model/CMSProBaseService/attachment-images.model';
import { IRFIComments } from '@/shared/model/CMSProBaseService/rfi-comments.model';
import { IRFITimeLine } from '@/shared/model/CMSProBaseService/rfi-time-line.model';

export interface IRFI {
  id?: string;
  title?: string;
  question?: string;
  answer?: string;
  sentDate?: Date;
  dueDate?: Date;
  locked?: boolean;
  lockedBy?: string;
  statusDescription?: string;
  statusId?: string;
  taskImages?: ITaskAttachmentImages[];
  taskAttachments?: ITaskAttachmentOthers[];
  sheetAttachments?: IAttachmentOthers[];
  sheetImages?: IAttachmentImages[];
  rfiComments?: IRFIComments[];
  timeLines?: IRFITimeLine[];
  projectName?: string;
  projectId?: string;
}

export class RFI implements IRFI {
  constructor(
    public id?: string,
    public title?: string,
    public question?: string,
    public answer?: string,
    public sentDate?: Date,
    public dueDate?: Date,
    public locked?: boolean,
    public lockedBy?: string,
    public statusDescription?: string,
    public statusId?: string,
    public taskImages?: ITaskAttachmentImages[],
    public taskAttachments?: ITaskAttachmentOthers[],
    public sheetAttachments?: IAttachmentOthers[],
    public sheetImages?: IAttachmentImages[],
    public rfiComments?: IRFIComments[],
    public timeLines?: IRFITimeLine[],
    public projectName?: string,
    public projectId?: string
  ) {
    this.locked = this.locked || false;
  }
}
