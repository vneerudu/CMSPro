import { ISheetTags } from '@/shared/model/CMSProBaseService/sheet-tags.model';
import { ISheetHistory } from '@/shared/model/CMSProBaseService/sheet-history.model';
import { IAttachments } from '@/shared/model/CMSProBaseService/attachments.model';
import { ISheetComments } from '@/shared/model/CMSProBaseService/sheet-comments.model';

export interface ISheets {
  id?: string;
  number?: number;
  title?: string;
  version?: string;
  createdBy?: string;
  creationDate?: Date;
  documentsId?: string;
  tags?: ISheetTags[];
  histories?: ISheetHistory[];
  attachments?: IAttachments[];
  comments?: ISheetComments[];
  taskId?: string;
  projectName?: string;
  projectId?: string;
}

export class Sheets implements ISheets {
  constructor(
    public id?: string,
    public number?: number,
    public title?: string,
    public version?: string,
    public createdBy?: string,
    public creationDate?: Date,
    public documentsId?: string,
    public tags?: ISheetTags[],
    public histories?: ISheetHistory[],
    public attachments?: IAttachments[],
    public comments?: ISheetComments[],
    public taskId?: string,
    public projectName?: string,
    public projectId?: string
  ) {}
}
