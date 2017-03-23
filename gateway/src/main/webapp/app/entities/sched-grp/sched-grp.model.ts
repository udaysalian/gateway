import { Section } from '../section';
export class SchedGrp {
    constructor(
        public id?: number,
        public schedGrpId?: number,
        public description?: string,
        public rcptSection?: Section,
        public dlvrySection?: Section,
    ) { }
}
