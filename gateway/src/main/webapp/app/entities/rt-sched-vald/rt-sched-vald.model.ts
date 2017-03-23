import { RtSched } from '../rt-sched';
export class RtSchedVald {
    constructor(
        public id?: number,
        public validType?: string,
        public updater?: string,
        public updateTimeStamp?: any,
        public rtSched?: RtSched,
    ) { }
}
