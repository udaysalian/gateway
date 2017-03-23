import { RtSched } from '../rt-sched';
import { ContrLoc } from '../contr-loc';
import { BusinessAssociate } from '../business-associate';
export class Contract {
    constructor(
        public id?: number,
        public contrId?: string,
        public updater?: string,
        public updateTimeStamp?: any,
        public rtSched?: RtSched,
        public contrLoc?: ContrLoc,
        public businessAssociate?: BusinessAssociate,
    ) { }
}
