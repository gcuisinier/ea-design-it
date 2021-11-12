import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILandscapeView } from '@/shared/model/landscape-view.model';
import LandscapeViewService from './landscape-view.service';
import AlertService from '@/shared/alert/alert.service';
import { FlowImport, IFlowImport } from '@/shared/model/flow-import.model';

@Component
export default class LandscapeViewDetails extends Vue {
  @Inject('landscapeViewService') private landscapeViewService: () => LandscapeViewService;
  @Inject('alertService') private alertService: () => AlertService;

  public landscapeView: ILandscapeView = {};
  public plantUMLImage = '';
  public captions = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.landscapeViewId) {
        vm.retrieveLandscapeView(to.params.landscapeViewId);
      }
    });
  }

  public retrieveLandscapeView(landscapeViewId) {
    this.landscapeViewService()
      .find(landscapeViewId)
      .then(res => {
        this.landscapeView = res;
        this.fillCaption();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
    this.getPlantUML(landscapeViewId);
  }

  public fillCaption() {
    this.landscapeView.flows.forEach(flow => {
      flow.interfaces.forEach(inter => {
        var caption = {
          flowAlias: flow.alias,
          flowID: flow.id,
          interfaceAlias: inter.alias,
          interfaceID: inter.id,
          description: flow.description,
          protocol: inter.protocol,
          source: inter.source.name,
          target: inter.target.name,
        };
        this.captions.push(caption);
      });
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public getPlantUML(landscapeViewId) {
    console.log('Entering in method getPlantUML');
    this.landscapeViewService()
      .getPlantUML(landscapeViewId)
      .then(
        res => {
          this.plantUMLImage = res.data;
        },
        err => {
          console.log(err);
        }
      );
  }
}
