export interface ISheetTags {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  sheetsId?: string;
}

export class SheetTags implements ISheetTags {
  constructor(public id?: string, public code?: string, public description?: string, public isActive?: boolean, public sheetsId?: string) {
    this.isActive = this.isActive || false;
  }
}
