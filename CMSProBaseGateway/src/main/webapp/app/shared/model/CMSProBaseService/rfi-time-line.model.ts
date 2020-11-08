export interface IRFITimeLine {
  id?: string;
  createdBy?: string;
  message?: string;
  creationDate?: Date;
  rfiTitle?: string;
  rfiId?: string;
}

export class RFITimeLine implements IRFITimeLine {
  constructor(
    public id?: string,
    public createdBy?: string,
    public message?: string,
    public creationDate?: Date,
    public rfiTitle?: string,
    public rfiId?: string
  ) {}
}
