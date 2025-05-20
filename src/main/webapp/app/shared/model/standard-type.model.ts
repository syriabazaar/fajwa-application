export interface IStandardType {
  id?: number;
  name?: string | null;
}

export class StandardType implements IStandardType {
  constructor(
    public id?: number,
    public name?: string | null,
  ) {}
}
