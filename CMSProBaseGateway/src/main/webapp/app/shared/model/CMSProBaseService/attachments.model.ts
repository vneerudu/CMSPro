export interface IAttachments {
  id?: string;
  folder?: string;
  fileName?: string;
  createdBy?: string;
  creationDate?: Date;
  imageFileName?: string;
  imageId?: string;
  pdfattachmentFileName?: string;
  pdfattachmentId?: string;
  sheetNumber?: string;
  sheetId?: string;
}

export class Attachments implements IAttachments {
  constructor(
    public id?: string,
    public folder?: string,
    public fileName?: string,
    public createdBy?: string,
    public creationDate?: Date,
    public imageFileName?: string,
    public imageId?: string,
    public pdfattachmentFileName?: string,
    public pdfattachmentId?: string,
    public sheetNumber?: string,
    public sheetId?: string
  ) {}
}
