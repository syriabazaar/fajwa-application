export interface IDepartment {
  id?: number;
  depName?: string | null;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public depName?: string | null,
  ) {}
}
