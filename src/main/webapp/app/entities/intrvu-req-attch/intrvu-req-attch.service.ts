import axios from 'axios';

import { type IIntrvuReqAttch } from '@/shared/model/intrvu-req-attch.model';

const baseApiUrl = 'api/intrvu-req-attches';

export default class IntrvuReqAttchService {
  find(id: number): Promise<IIntrvuReqAttch> {
    return new Promise<IIntrvuReqAttch>((resolve, reject) => {
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

  delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  create(entity: IIntrvuReqAttch): Promise<IIntrvuReqAttch> {
    return new Promise<IIntrvuReqAttch>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  update(entity: IIntrvuReqAttch): Promise<IIntrvuReqAttch> {
    return new Promise<IIntrvuReqAttch>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  partialUpdate(entity: IIntrvuReqAttch): Promise<IIntrvuReqAttch> {
    return new Promise<IIntrvuReqAttch>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
