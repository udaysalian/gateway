import { BusinessAssociate } from '../business-associate';
import { Contact } from '../contact';
import { BusinessAssociateAddress } from '../business-associate-address';
export class BusinessAssociateContact {
    constructor(
        public id?: number,
        public businessAssociateContactId?: number,
        public beginDate?: any,
        public endDate?: any,
        public businessAssociate?: BusinessAssociate,
        public contact?: Contact,
        public mailAddress?: BusinessAssociateAddress,
        public deliveryAddress?: BusinessAssociateAddress,
    ) { }
}
