export interface ISheetComments {
  id?: string;
  createdBy?: string;
  comment?: string;
  creationDate?: Date;
  sheetNumber?: string;
  sheetId?: string;
}

export class SheetComments implements ISheetComments {
  constructor(
    public id?: string,
    public createdBy?: string,
    public comment?: string,
    public creationDate?: Date,
    public sheetNumber?: string,
    public sheetId?: string
  ) {}
}
