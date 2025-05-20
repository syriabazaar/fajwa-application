import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import AppointmentService from './appointment.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Appointment } from '@/shared/model/appointment.model';

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
  describe('Appointment Service', () => {
    let service: AppointmentService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AppointmentService();
      currentDate = new Date();
      elemDefault = new Appointment(123, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          appDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          interveiewDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
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

      it('should create a Appointment', async () => {
        const returnedFromService = {
          id: 123,
          appDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          interveiewDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { appDateTime: currentDate, interveiewDate: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Appointment', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Appointment', async () => {
        const returnedFromService = {
          appDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          interveiewDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };

        const expected = { appDateTime: currentDate, interveiewDate: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Appointment', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Appointment', async () => {
        const patchObject = {
          appDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          interveiewDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...new Appointment(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { appDateTime: currentDate, interveiewDate: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Appointment', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Appointment', async () => {
        const returnedFromService = {
          appDateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          interveiewDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          ...elemDefault,
        };
        const expected = { appDateTime: currentDate, interveiewDate: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Appointment', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Appointment', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Appointment', async () => {
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
