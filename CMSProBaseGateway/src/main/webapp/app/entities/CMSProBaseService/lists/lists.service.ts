import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ILists } from '@/shared/model/CMSProBaseService/lists.model';

const baseApiUrl = 'services/cmsprobaseservice/api/lists';
const baseSearchApiUrl = 'services/cmsprobaseservice/api/_search/lists?query=';

export default class ListsService {
  public search(query, paginationQuery): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseSearchApiUrl}${query}&${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public find(id: string): Promise<ILists> {
    return new Promise<ILists>((resolve, reject) => {
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

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: string): Promise<any> {
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

  public create(entity: ILists): Promise<ILists> {
    return new Promise<ILists>((resolve, reject) => {
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

  public update(entity: ILists): Promise<ILists> {
    return new Promise<ILists>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
