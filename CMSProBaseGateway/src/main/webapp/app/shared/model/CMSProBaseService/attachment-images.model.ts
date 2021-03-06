export interface IAttachmentImages {
  id?: string;
  fileName?: string;
  fileType?: string;
  contentContentType?: string;
  content?: any;
  createdBy?: string;
  creationDate?: Date;
  attachmentId?: string;
  rfiTitle?: string;
  rfiId?: string;
}

export class AttachmentImages implements IAttachmentImages {
  constructor(
    public id?: string,
    public fileName?: string,
    public fileType?: string,
    public contentContentType?: string,
    public content?: any,
    public createdBy?: string,
    public creationDate?: Date,
    public attachmentId?: string,
    public rfiTitle?: string,
    public rfiId?: string
  ) {}
}
