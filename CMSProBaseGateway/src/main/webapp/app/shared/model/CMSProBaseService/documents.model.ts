export interface IDocuments {
  id?: string;
  fileName?: string;
  fileType?: string;
  contentContentType?: string;
  content?: any;
  createdBy?: string;
  creationDate?: Date;
  sheetsId?: string;
}

export class Documents implements IDocuments {
  constructor(
    public id?: string,
    public fileName?: string,
    public fileType?: string,
    public contentContentType?: string,
    public content?: any,
    public createdBy?: string,
    public creationDate?: Date,
    public sheetsId?: string
  ) {}
}
