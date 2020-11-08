export interface IRFIComments {
  id?: string;
  createdBy?: string;
  comment?: string;
  creationDate?: Date;
  rfiTitle?: string;
  rfiId?: string;
}

export class RFIComments implements IRFIComments {
  constructor(
    public id?: string,
    public createdBy?: string,
    public comment?: string,
    public creationDate?: Date,
    public rfiTitle?: string,
    public rfiId?: string
  ) {}
}
