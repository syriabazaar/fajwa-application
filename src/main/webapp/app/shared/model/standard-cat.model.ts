import { type IStandardParent } from '@/shared/model/standard-parent.model';
import { type IStandardReqAttach } from '@/shared/model/standard-req-attach.model';

export interface IStandardCat {
  id?: number;
  name?: string | null;
  standardParent?: IStandardParent | null;
  reqAttachment?: IStandardReqAttach | null;
}

export class StandardCat implements IStandardCat {
  constructor(
    public id?: number,
    public name?: string | null,
    public standardParent?: IStandardParent | null,
    public reqAttachment?: IStandardReqAttach | null,
  ) {}
}
