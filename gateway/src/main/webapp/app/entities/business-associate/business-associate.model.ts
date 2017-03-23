import { BusinessAssociateContact } from '../business-associate-contact';
import { BusinessAssociateAddress } from '../business-associate-address';
export class BusinessAssociate {
    constructor(
        public id?: number,
        public businessAssociateId?: number,
        public baName?: string,
        public baAbbr?: string,
        public baNbr?: string,
        public baDunsNbr?: string,
        public businessAssociateContact?: BusinessAssociateContact,
        public businessAssociateAddress?: BusinessAssociateAddress,
    ) { }
}
