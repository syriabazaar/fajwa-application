import { type IStandardType } from '@/shared/model/standard-type.model';

export interface IJobDesc {
  id?: number;
  jobName?: string | null;
  standardType?: IStandardType | null;
}

export class JobDesc implements IJobDesc {
  constructor(
    public id?: number,
    public jobName?: string | null,
    public standardType?: IStandardType | null,
  ) {}
}
