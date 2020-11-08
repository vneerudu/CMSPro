export interface ISheetHistory {
  id?: string;
  number?: number;
  version?: string;
  isActive?: boolean;
  createdBy?: string;
  creationDate?: Date;
  sheetNumber?: string;
  sheetId?: string;
}

export class SheetHistory implements ISheetHistory {
  constructor(
    public id?: string,
    public number?: number,
    public version?: string,
    public isActive?: boolean,
    public createdBy?: string,
    public creationDate?: Date,
    public sheetNumber?: string,
    public sheetId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
