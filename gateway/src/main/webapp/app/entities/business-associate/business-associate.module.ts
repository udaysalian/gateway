import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    BusinessAssociateService,
    BusinessAssociatePopupService,
    BusinessAssociateComponent,
    BusinessAssociateDetailComponent,
    BusinessAssociateDialogComponent,
    BusinessAssociatePopupComponent,
    BusinessAssociateDeletePopupComponent,
    BusinessAssociateDeleteDialogComponent,
    businessAssociateRoute,
    businessAssociatePopupRoute,
} from './';

let ENTITY_STATES = [
    ...businessAssociateRoute,
    ...businessAssociatePopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessAssociateComponent,
        BusinessAssociateDetailComponent,
        BusinessAssociateDialogComponent,
        BusinessAssociateDeleteDialogComponent,
        BusinessAssociatePopupComponent,
        BusinessAssociateDeletePopupComponent,
    ],
    entryComponents: [
        BusinessAssociateComponent,
        BusinessAssociateDialogComponent,
        BusinessAssociatePopupComponent,
        BusinessAssociateDeleteDialogComponent,
        BusinessAssociateDeletePopupComponent,
    ],
    providers: [
        BusinessAssociateService,
        BusinessAssociatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraBusinessAssociateModule {}
