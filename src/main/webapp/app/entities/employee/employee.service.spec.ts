import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import EmployeeService from './employee.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Employee } from '@/shared/model/employee.model';

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
  describe('Employee Service', () => {
    let service: EmployeeService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new EmployeeService();
      currentDate = new Date();
      elemDefault = new Employee(
        123,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          dateOfHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          dateOfLastHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          personStartDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          originalDateOfHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          adjustedServiceDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should return a list of Employee', async () => {
        const returnedFromService = {
          fullname: 'BBBBBB',
          gradeId: 1,
          gradeName: 'BBBBBB',
          homePhone: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          assigmentId: 1,
          dateOfAssignment: 'BBBBBB',
          jobName: 'BBBBBB',
          address: 'BBBBBB',
          organizationId: 1,
          organization: 'BBBBBB',
          nationalIdentifier: 'BBBBBB',
          uid: 'BBBBBB',
          parentDepartmentId: 'BBBBBB',
          slmMilitaryFlag: 1,
          militaryFirstName: 'BBBBBB',
          slmName: 'BBBBBB',
          age: 1,
          dateOfHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          dateOfLastHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          gender: 'BBBBBB',
          jobId: 1,
          nationalityCode: 'BBBBBB',
          nationality: 'BBBBBB',
          personStartDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          originalDateOfHire: dayjs(currentDate).format(DATE_TIME_FORMAT),
          supervisorId: 'BBBBBB',
          supervisorFullName: 'BBBBBB',
          parentDepartmentName: 'BBBBBB',
          sectionId: 1,
          sectionName: 'BBBBBB',
          adjustedServiceDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          qualificationType: 'BBBBBB',
          qualificationSpecification: 'BBBBBB',
          ...elemDefault,
        };
        const expected = {
          dateOfHire: currentDate,
          dateOfLastHire: currentDate,
          personStartDate: currentDate,
          originalDateOfHire: currentDate,
          adjustedServiceDate: currentDate,
          ...returnedFromService,
        };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Employee', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
