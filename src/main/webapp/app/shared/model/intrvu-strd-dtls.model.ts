import { type IStandarditem } from '@/shared/model/standarditem.model';
import { type IAppointment } from '@/shared/model/appointment.model';

import { type StandardOption } from '@/shared/model/enumerations/standard-option.model';
export interface IIntrvuStrdDtls {
  id?: number;
  standardOption?: keyof typeof StandardOption | null;
  standarditem?: IStandarditem | null;
  appointment?: IAppointment | null;
}

export class IntrvuStrdDtls implements IIntrvuStrdDtls {
  constructor(
    public id?: number,
    public standardOption?: keyof typeof StandardOption | null,
    public standarditem?: IStandarditem | null,
    public appointment?: IAppointment | null,
  ) {}
}
