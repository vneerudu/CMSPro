/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import RFIService from '@/entities/CMSProBaseService/rfi/rfi.service';
import { RFI } from '@/shared/model/CMSProBaseService/rfi.model';

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
  describe('RFI Service', () => {
    let service: RFIService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new RFIService();
      currentDate = new Date();

      elemDefault = new RFI('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            sentDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
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

      it('should create a RFI', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            sentDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sentDate: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a RFI', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a RFI', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            question: 'BBBBBB',
            answer: 'BBBBBB',
            sentDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            locked: true,
            lockedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentDate: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a RFI', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of RFI', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            question: 'BBBBBB',
            answer: 'BBBBBB',
            sentDate: format(currentDate, DATE_FORMAT),
            dueDate: format(currentDate, DATE_FORMAT),
            locked: true,
            lockedBy: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            sentDate: currentDate,
            dueDate: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of RFI', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a RFI', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete('123').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a RFI', async () => {
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
