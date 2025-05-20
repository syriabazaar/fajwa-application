import { type IEmployee } from '@/shared/model/employee.model';
import { type IJobVacant } from '@/shared/model/job-vacant.model';

export interface INomination {
  id?: number;
  machPerc?: number | null;
  employee?: IEmployee | null;
  jobVacant?: IJobVacant | null;
}

export class Nomination implements INomination {
  constructor(
    public id?: number,
    public machPerc?: number | null,
    public employee?: IEmployee | null,
    public jobVacant?: IJobVacant | null,
  ) {}
}
