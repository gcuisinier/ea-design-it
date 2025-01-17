import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import CapabilityImportService from './capability-import.service';
import AlertService from '@/shared/alert/alert.service';
import { ICapabilityImport } from '@/shared/model/capability-import.model';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CapabilityImport extends Vue {
  @Inject('capabilityImportService') private capabilityImportService: () => CapabilityImportService;
  @Inject('alertService') private alertService: () => AlertService;

  public capabilitiesImports: ICapabilityImport[] = [];
  public filteredCapabilitiesImports: ICapabilityImport[] = [];
  public excelFile: File = null;
  public isFetching = false;
  public fileSubmited = false;
  public rowsLoaded = false;
  public excelFileName = 'Browse File';

  public handleFileUpload(event): void {
    this.excelFile = event.target.files[0];
    this.excelFileName = this.excelFile.name;
  }

  public submitFile(): void {
    this.isFetching = true;
    this.fileSubmited = true;
    this.capabilitiesImports = [];
    this.filteredCapabilitiesImports = [];
    this.capabilityImportService()
      .uploadFile(this.excelFile)
      .then(
        res => {
          this.capabilitiesImports = res.data;
          this.filteredCapabilitiesImports = res.data;
          this.isFetching = false;
          this.rowsLoaded = true;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  // Error Handling

  public filterErrors() {
    this.filteredCapabilitiesImports = this.filteredCapabilitiesImports.filter(c => c.status === 'ERROR');
  }
}
