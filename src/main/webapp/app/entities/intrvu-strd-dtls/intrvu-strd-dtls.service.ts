import axios from 'axios';

import { type IIntrvuStrdDtls } from '@/shared/model/intrvu-strd-dtls.model';

const baseApiUrl = 'api/intrvu-strd-dtls';

export default class IntrvuStrdDtlsService {
  find(id: number): Promise<IIntrvuStrdDtls> {
    return new Promise<IIntrvuStrdDtls>((resolve, reject) => {
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

  create(entity: IIntrvuStrdDtls): Promise<IIntrvuStrdDtls> {
    return new Promise<IIntrvuStrdDtls>((resolve, reject) => {
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

  update(entity: IIntrvuStrdDtls): Promise<IIntrvuStrdDtls> {
    return new Promise<IIntrvuStrdDtls>((resolve, reject) => {
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

  partialUpdate(entity: IIntrvuStrdDtls): Promise<IIntrvuStrdDtls> {
    return new Promise<IIntrvuStrdDtls>((resolve, reject) => {
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
