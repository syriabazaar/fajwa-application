import { type IStandardType } from '@/shared/model/standard-type.model';

export interface IStandardParent {
  id?: number;
  name?: string | null;
  standardType?: IStandardType | null;
}

export class StandardParent implements IStandardParent {
  constructor(
    public id?: number,
    public name?: string | null,
    public standardType?: IStandardType | null,
  ) {}
}
