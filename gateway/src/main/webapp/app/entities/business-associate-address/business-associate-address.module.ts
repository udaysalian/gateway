import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    BusinessAssociateAddressService,
    BusinessAssociateAddressPopupService,
    BusinessAssociateAddressComponent,
    BusinessAssociateAddressDetailComponent,
    BusinessAssociateAddressDialogComponent,
    BusinessAssociateAddressPopupComponent,
    BusinessAssociateAddressDeletePopupComponent,
    BusinessAssociateAddressDeleteDialogComponent,
    businessAssociateAddressRoute,
    businessAssociateAddressPopupRoute,
} from './';

let ENTITY_STATES = [
    ...businessAssociateAddressRoute,
    ...businessAssociateAddressPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessAssociateAddressComponent,
        BusinessAssociateAddressDetailComponent,
        BusinessAssociateAddressDialogComponent,
        BusinessAssociateAddressDeleteDialogComponent,
        BusinessAssociateAddressPopupComponent,
        BusinessAssociateAddressDeletePopupComponent,
    ],
    entryComponents: [
        BusinessAssociateAddressComponent,
        BusinessAssociateAddressDialogComponent,
        BusinessAssociateAddressPopupComponent,
        BusinessAssociateAddressDeleteDialogComponent,
        BusinessAssociateAddressDeletePopupComponent,
    ],
    providers: [
        BusinessAssociateAddressService,
        BusinessAssociateAddressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraBusinessAssociateAddressModule {}
