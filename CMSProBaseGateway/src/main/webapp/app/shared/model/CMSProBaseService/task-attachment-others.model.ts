export interface ITaskAttachmentOthers {
  id?: string;
  fileName?: string;
  fileType?: string;
  contentContentType?: string;
  content?: any;
  createdBy?: string;
  creationDate?: Date;
  taskTitle?: string;
  taskId?: string;
  rfiTitle?: string;
  rfiId?: string;
}

export class TaskAttachmentOthers implements ITaskAttachmentOthers {
  constructor(
    public id?: string,
    public fileName?: string,
    public fileType?: string,
    public contentContentType?: string,
    public content?: any,
    public createdBy?: string,
    public creationDate?: Date,
    public taskTitle?: string,
    public taskId?: string,
    public rfiTitle?: string,
    public rfiId?: string
  ) {}
}
