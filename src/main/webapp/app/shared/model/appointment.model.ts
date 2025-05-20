import { type INomination } from '@/shared/model/nomination.model';

export interface IAppointment {
  id?: number;
  appDateTime?: Date | null;
  interveiewDate?: Date | null;
  nomination?: INomination | null;
}

export class Appointment implements IAppointment {
  constructor(
    public id?: number,
    public appDateTime?: Date | null,
    public interveiewDate?: Date | null,
    public nomination?: INomination | null,
  ) {}
}
