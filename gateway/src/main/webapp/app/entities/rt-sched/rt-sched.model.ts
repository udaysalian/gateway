import { RtSchedVald } from '../rt-sched-vald';
export class RtSched {
    constructor(
        public id?: number,
        public rsType?: string,
        public updater?: string,
        public updateTimeStamp?: any,
        public rtSchedVald?: RtSchedVald,
    ) { }
}
