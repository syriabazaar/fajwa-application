import { type IDepartment } from '@/shared/model/department.model';
import { type IJobDesc } from '@/shared/model/job-desc.model';

export interface IJobVacant {
  id?: number;
  department?: IDepartment | null;
  jobDesc?: IJobDesc | null;
}

export class JobVacant implements IJobVacant {
  constructor(
    public id?: number,
    public department?: IDepartment | null,
    public jobDesc?: IJobDesc | null,
  ) {}
}
