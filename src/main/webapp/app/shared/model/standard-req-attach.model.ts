export interface IStandardReqAttach {
  id?: number;
  name?: string | null;
  attDesc?: string | null;
}

export class StandardReqAttach implements IStandardReqAttach {
  constructor(
    public id?: number,
    public name?: string | null,
    public attDesc?: string | null,
  ) {}
}
