import { Component, Vue, Inject } from 'vue-property-decorator';
import { Capability } from '@/shared/model/capability.model';
import { ICapability } from '@/shared/model/capability.model';
import { PropType } from 'vue';
import CapabilityComponentItem from '@/entities/capability/component/capability-item.vue';

const CapabilityProps = Vue.extend({
  props: {
    capability: {
      type: Object as PropType<ICapability>,
    },
    path: {
      type: Array as PropType<ICapability[]>,
    },
    nbLevel: {
      type: Number,
      default: 1,
    },
    menu: {
      type: Boolean,
      default: true,
    },
    defaultShowApplications: {
      type: Boolean,
      default: true,
    },
  },
});

@Component({
  components: {
    CapabilityComponentItem,
  },
})
export default class CapabilityComponent extends CapabilityProps {
  public showApplications: Boolean = this.defaultShowApplications;

  public retrieveCapability(capId: number) {
    console.log(capId);
    this.$emit('retrieveCapability', capId);
  }
}
