import { Nom } from '../nom';
export class NomPrty {
    constructor(
        public id?: number,
        public contrId?: string,
        public activityNbr?: string,
        public gasDate?: any,
        public prtyTp?: string,
        public prtyQty?: number,
        public subType?: string,
        public dirOfFlow?: string,
        public updater?: string,
        public updateTimeStamp?: any,
        public nom?: Nom,
    ) { }
}
