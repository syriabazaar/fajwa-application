import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import IntrvuReqAttchService from './intrvu-req-attch.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { IntrvuReqAttch } from '@/shared/model/intrvu-req-attch.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('IntrvuReqAttch Service', () => {
    let service: IntrvuReqAttchService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new IntrvuReqAttchService();
      currentDate = new Date();
      elemDefault = new IntrvuReqAttch(123, currentDate, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { addDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a IntrvuReqAttch', async () => {
        const returnedFromService = { id: 123, addDate: dayjs(currentDate).format(DATE_TIME_FORMAT), ...elemDefault };
        const expected = { addDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a IntrvuReqAttch', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a IntrvuReqAttch', async () => {
        const returnedFromService = { addDate: dayjs(currentDate).format(DATE_TIME_FORMAT), attch: 'BBBBBB', ...elemDefault };

        const expected = { addDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a IntrvuReqAttch', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a IntrvuReqAttch', async () => {
        const patchObject = { ...new IntrvuReqAttch() };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { addDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a IntrvuReqAttch', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of IntrvuReqAttch', async () => {
        const returnedFromService = { addDate: dayjs(currentDate).format(DATE_TIME_FORMAT), attch: 'BBBBBB', ...elemDefault };
        const expected = { addDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of IntrvuReqAttch', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a IntrvuReqAttch', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a IntrvuReqAttch', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
