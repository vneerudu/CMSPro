export interface IMenuItems {
  id?: string;
  code?: string;
  description?: string;
  isActive?: boolean;
  userRolesDescription?: string;
  userRolesId?: string;
}

export class MenuItems implements IMenuItems {
  constructor(
    public id?: string,
    public code?: string,
    public description?: string,
    public isActive?: boolean,
    public userRolesDescription?: string,
    public userRolesId?: string
  ) {
    this.isActive = this.isActive || false;
  }
}
