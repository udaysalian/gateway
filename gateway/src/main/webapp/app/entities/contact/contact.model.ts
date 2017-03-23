import { BusinessAssociate } from '../business-associate';
export class Contact {
    constructor(
        public id?: number,
        public contactId?: number,
        public firstName?: string,
        public lastName?: string,
        public businessAssociate?: BusinessAssociate,
    ) { }
}
