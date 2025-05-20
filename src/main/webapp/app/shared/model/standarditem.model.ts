import { type IStandardCat } from '@/shared/model/standard-cat.model';

export interface IStandarditem {
  id?: number;
  name?: string | null;
  isActive?: boolean | null;
  weightPercentage?: number | null;
  standardCat?: IStandardCat | null;
}

export class Standarditem implements IStandarditem {
  constructor(
    public id?: number,
    public name?: string | null,
    public isActive?: boolean | null,
    public weightPercentage?: number | null,
    public standardCat?: IStandardCat | null,
  ) {
    this.isActive = this.isActive ?? false;
  }
}
