import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    BusinessAssociateContactService,
    BusinessAssociateContactPopupService,
    BusinessAssociateContactComponent,
    BusinessAssociateContactDetailComponent,
    BusinessAssociateContactDialogComponent,
    BusinessAssociateContactPopupComponent,
    BusinessAssociateContactDeletePopupComponent,
    BusinessAssociateContactDeleteDialogComponent,
    businessAssociateContactRoute,
    businessAssociateContactPopupRoute,
} from './';

let ENTITY_STATES = [
    ...businessAssociateContactRoute,
    ...businessAssociateContactPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessAssociateContactComponent,
        BusinessAssociateContactDetailComponent,
        BusinessAssociateContactDialogComponent,
        BusinessAssociateContactDeleteDialogComponent,
        BusinessAssociateContactPopupComponent,
        BusinessAssociateContactDeletePopupComponent,
    ],
    entryComponents: [
        BusinessAssociateContactComponent,
        BusinessAssociateContactDialogComponent,
        BusinessAssociateContactPopupComponent,
        BusinessAssociateContactDeleteDialogComponent,
        BusinessAssociateContactDeletePopupComponent,
    ],
    providers: [
        BusinessAssociateContactService,
        BusinessAssociateContactPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraBusinessAssociateContactModule {}
