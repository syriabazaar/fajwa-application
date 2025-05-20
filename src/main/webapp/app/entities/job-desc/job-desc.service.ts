import axios from 'axios';

import { type IJobDesc } from '@/shared/model/job-desc.model';

const baseApiUrl = 'api/job-descs';

export default class JobDescService {
  find(id: number): Promise<IJobDesc> {
    return new Promise<IJobDesc>((resolve, reject) => {
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

  retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
