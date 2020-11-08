import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import DocumentsService from '../documents/documents.service';
import { IDocuments } from '@/shared/model/CMSProBaseService/documents.model';

import SheetTagsService from '../sheet-tags/sheet-tags.service';
import { ISheetTags } from '@/shared/model/CMSProBaseService/sheet-tags.model';

import SheetHistoryService from '../sheet-history/sheet-history.service';
import { ISheetHistory } from '@/shared/model/CMSProBaseService/sheet-history.model';

import AttachmentsService from '../attachments/attachments.service';
import { IAttachments } from '@/shared/model/CMSProBaseService/attachments.model';

import SheetCommentsService from '../sheet-comments/sheet-comments.service';
import { ISheetComments } from '@/shared/model/CMSProBaseService/sheet-comments.model';

import TasksService from '../tasks/tasks.service';
import { ITasks } from '@/shared/model/CMSProBaseService/tasks.model';

import ProjectsService from '../projects/projects.service';
import { IProjects } from '@/shared/model/CMSProBaseService/projects.model';

import AlertService from '@/shared/alert/alert.service';
import { ISheets, Sheets } from '@/shared/model/CMSProBaseService/sheets.model';
import SheetsService from './sheets.service';

const validations: any = {
  sheets: {
    number: {
      required,
      numeric,
    },
    title: {},
    version: {
      required,
    },
    createdBy: {
      required,
    },
    creationDate: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class SheetsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sheetsService') private sheetsService: () => SheetsService;
  public sheets: ISheets = new Sheets();

  @Inject('documentsService') private documentsService: () => DocumentsService;

  public documents: IDocuments[] = [];

  @Inject('sheetTagsService') private sheetTagsService: () => SheetTagsService;

  public sheetTags: ISheetTags[] = [];

  @Inject('sheetHistoryService') private sheetHistoryService: () => SheetHistoryService;

  public sheetHistories: ISheetHistory[] = [];

  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;

  public attachments: IAttachments[] = [];

  @Inject('sheetCommentsService') private sheetCommentsService: () => SheetCommentsService;

  public sheetComments: ISheetComments[] = [];

  @Inject('tasksService') private tasksService: () => TasksService;

  public tasks: ITasks[] = [];

  @Inject('projectsService') private projectsService: () => ProjectsService;

  public projects: IProjects[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sheetsId) {
        vm.retrieveSheets(to.params.sheetsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.sheets.id) {
      this.sheetsService()
        .update(this.sheets)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sheetsService()
        .create(this.sheets)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('cmsProBaseGatewayApp.cmsProBaseServiceSheets.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSheets(sheetsId): void {
    this.sheetsService()
      .find(sheetsId)
      .then(res => {
        this.sheets = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentsService()
      .retrieve()
      .then(res => {
        this.documents = res.data;
      });
    this.sheetTagsService()
      .retrieve()
      .then(res => {
        this.sheetTags = res.data;
      });
    this.sheetHistoryService()
      .retrieve()
      .then(res => {
        this.sheetHistories = res.data;
      });
    this.attachmentsService()
      .retrieve()
      .then(res => {
        this.attachments = res.data;
      });
    this.sheetCommentsService()
      .retrieve()
      .then(res => {
        this.sheetComments = res.data;
      });
    this.tasksService()
      .retrieve()
      .then(res => {
        this.tasks = res.data;
      });
    this.projectsService()
      .retrieve()
      .then(res => {
        this.projects = res.data;
      });
  }
}
