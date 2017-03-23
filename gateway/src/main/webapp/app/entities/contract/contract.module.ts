import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    ContractService,
    ContractPopupService,
    ContractComponent,
    ContractDetailComponent,
    ContractDialogComponent,
    ContractPopupComponent,
    ContractDeletePopupComponent,
    ContractDeleteDialogComponent,
    contractRoute,
    contractPopupRoute,
} from './';

let ENTITY_STATES = [
    ...contractRoute,
    ...contractPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContractComponent,
        ContractDetailComponent,
        ContractDialogComponent,
        ContractDeleteDialogComponent,
        ContractPopupComponent,
        ContractDeletePopupComponent,
    ],
    entryComponents: [
        ContractComponent,
        ContractDialogComponent,
        ContractPopupComponent,
        ContractDeleteDialogComponent,
        ContractDeletePopupComponent,
    ],
    providers: [
        ContractService,
        ContractPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraContractModule {}
