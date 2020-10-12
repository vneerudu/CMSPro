/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import ProjectsService from '@/entities/CMSProMicroService/projects/projects.service';
import { Projects } from '@/shared/model/CMSProMicroService/projects.model';

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
  describe('Projects Service', () => {
    let service: ProjectsService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new ProjectsService();
      currentDate = new Date();

      elemDefault = new Projects(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: format(currentDate, DATE_FORMAT),
            finishDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Projects', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: format(currentDate, DATE_FORMAT),
            finishDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            finishDate: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Projects', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Projects', async () => {
        const returnedFromService = Object.assign(
          {
            projectID: 1,
            name: 'BBBBBB',
            department: 'BBBBBB',
            organization: 'BBBBBB',
            startDate: format(currentDate, DATE_FORMAT),
            finishDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            finishDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Projects', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Projects', async () => {
        const returnedFromService = Object.assign(
          {
            projectID: 1,
            name: 'BBBBBB',
            department: 'BBBBBB',
            organization: 'BBBBBB',
            startDate: format(currentDate, DATE_FORMAT),
            finishDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            finishDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Projects', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Projects', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Projects', async () => {
        mockedAxios.delete.mockReturnValue(Promise.reject(error));

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
