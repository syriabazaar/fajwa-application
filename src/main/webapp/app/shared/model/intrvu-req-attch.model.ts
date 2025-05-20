import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';
import { type IEmployee } from '@/shared/model/employee.model';

export interface IIntrvuReqAttch {
  id?: number;
  addDate?: Date | null;
  attchContentType?: string | null;
  attch?: string | null;
  standardReqAttach?: IStandardReqAttach | null;
  employee?: IEmployee | null;
}

export class IntrvuReqAttch implements IIntrvuReqAttch {
  constructor(
    public id?: number,
    public addDate?: Date | null,
    public attchContentType?: string | null,
    public attch?: string | null,
    public standardReqAttach?: IStandardReqAttach | null,
    public employee?: IEmployee | null,
  ) {}
}
