/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import TasksService from '@/entities/CMSProBaseService/tasks/tasks.service';
import { Tasks } from '@/shared/model/CMSProBaseService/tasks.model';

const mockedAxios: any = axios;
const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn(),
}));

describe('Service Tests', () => {
  describe('Tasks Service', () => {
    let service: TasksService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new TasksService();
      currentDate = new Date();

      elemDefault = new Tasks(
        'ID',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            creationDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find('123').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));
        return service
          .find('123')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Tasks', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            startDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            creationDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            dueDate: currentDate,
            creationDate: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Tasks', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Tasks', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            startDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            description: 'BBBBBB',
            costImpact: true,
            costImpactComment: 'BBBBBB',
            scheduleImpact: true,
            scheduleImpactComment: 'BBBBBB',
            createdBy: 'BBBBBB',
            creationDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            dueDate: currentDate,
            creationDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Tasks', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Tasks', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            startDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            description: 'BBBBBB',
            costImpact: true,
            costImpactComment: 'BBBBBB',
            scheduleImpact: true,
            scheduleImpactComment: 'BBBBBB',
            createdBy: 'BBBBBB',
            creationDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            dueDate: currentDate,
            creationDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Tasks', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Tasks', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete('123').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Tasks', async () => {
        mockedAxios.delete.mockReturnValue(Promise.reject(error));

        return service
          .delete('123')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
