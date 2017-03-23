import { SchedGrp } from '../sched-grp';
export class SchedEvent {
    constructor(
        public id?: number,
        public eventId?: number,
        public workingCapacity?: number,
        public adjWorkingCapacity?: number,
        public status?: string,
        public eventType?: string,
        public updater?: string,
        public updateTimestamp?: any,
        public schedGrp?: SchedGrp,
    ) { }
}
