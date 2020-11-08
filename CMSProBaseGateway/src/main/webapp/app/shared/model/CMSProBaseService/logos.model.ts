export interface ILogos {
  id?: string;
  fileName?: string;
  fileType?: string;
  contentContentType?: string;
  content?: any;
  createdBy?: string;
  creationDate?: Date;
}

export class Logos implements ILogos {
  constructor(
    public id?: string,
    public fileName?: string,
    public fileType?: string,
    public contentContentType?: string,
    public content?: any,
    public createdBy?: string,
    public creationDate?: Date
  ) {}
}
