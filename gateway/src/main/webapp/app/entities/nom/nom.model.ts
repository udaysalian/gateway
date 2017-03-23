import { Activity } from '../activity';
import { Contract } from '../contract';
import { NomPrty } from '../nom-prty';
export class Nom {
    constructor(
        public id?: number,
        public activityNbr?: string,
        public gasDate?: any,
        public reqRcptQty?: number,
        public reqDlvryQty?: number,
        public oldSchdRcptQty?: number,
        public newSchdRcptQty?: number,
        public oldSchdDlvryQty?: number,
        public newSchdDlvryQty?: number,
        public updater?: string,
        public updateTimeStamp?: any,
        public activity?: Activity,
        public contr?: Contract,
        public prty?: NomPrty,
    ) { }
}
