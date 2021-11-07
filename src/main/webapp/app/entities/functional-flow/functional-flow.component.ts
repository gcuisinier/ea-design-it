import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFunctionalFlow } from '@/shared/model/functional-flow.model';

import FunctionalFlowService from './functional-flow.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class FunctionalFlow extends Vue {
  @Inject('functionalFlowService') private functionalFlowService: () => FunctionalFlowService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public functionalFlows: IFunctionalFlow[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFunctionalFlows();
  }

  public clear(): void {
    this.retrieveAllFunctionalFlows();
  }

  public retrieveAllFunctionalFlows(): void {
    this.isFetching = true;
    this.functionalFlowService()
      .retrieve()
      .then(
        res => {
          this.functionalFlows = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IFunctionalFlow): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFunctionalFlow(): void {
    this.functionalFlowService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A FunctionalFlow is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFunctionalFlows();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}