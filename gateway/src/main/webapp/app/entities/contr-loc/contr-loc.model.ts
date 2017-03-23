import { Contract } from '../contract';
export class ContrLoc {
    constructor(
        public id?: number,
        public location?: string,
        public updater?: string,
        public updateTimeStamp?: any,
        public contract?: Contract,
    ) { }
}
