import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IEmployee } from '@/shared/model/employee.model';

const baseApiUrl = 'api/employees';

export default class EmployeeService {
  find(id: number): Promise<IEmployee> {
    return new Promise<IEmployee>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
