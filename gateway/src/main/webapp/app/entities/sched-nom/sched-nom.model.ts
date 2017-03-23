import { SchedEvent } from '../sched-event';
export class SchedNom {
    constructor(
        public id?: number,
        public contrId?: string,
        public activityNbr?: string,
        public gasDate?: any,
        public oldSchdRcptQty?: number,
        public newSchdRcptQty?: number,
        public oldSchdDlvryQty?: number,
        public newSchdDlvryQty?: number,
        public upater?: string,
        public updateTimeStamp?: any,
        public schedEvent?: SchedEvent,
    ) { }
}
