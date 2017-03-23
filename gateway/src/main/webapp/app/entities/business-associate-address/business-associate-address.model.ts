import { BusinessAssociate } from '../business-associate';
export class BusinessAssociateAddress {
    constructor(
        public id?: number,
        public businessAssociateAddressId?: number,
        public addLine1?: string,
        public addressNbr?: string,
        public addLine2?: string,
        public city?: string,
        public state?: string,
        public zipCode?: string,
        public businessAssociate?: BusinessAssociate,
    ) { }
}
