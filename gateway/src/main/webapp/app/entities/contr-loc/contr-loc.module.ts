import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    ContrLocService,
    ContrLocPopupService,
    ContrLocComponent,
    ContrLocDetailComponent,
    ContrLocDialogComponent,
    ContrLocPopupComponent,
    ContrLocDeletePopupComponent,
    ContrLocDeleteDialogComponent,
    contrLocRoute,
    contrLocPopupRoute,
} from './';

let ENTITY_STATES = [
    ...contrLocRoute,
    ...contrLocPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContrLocComponent,
        ContrLocDetailComponent,
        ContrLocDialogComponent,
        ContrLocDeleteDialogComponent,
        ContrLocPopupComponent,
        ContrLocDeletePopupComponent,
    ],
    entryComponents: [
        ContrLocComponent,
        ContrLocDialogComponent,
        ContrLocPopupComponent,
        ContrLocDeleteDialogComponent,
        ContrLocDeletePopupComponent,
    ],
    providers: [
        ContrLocService,
        ContrLocPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraContrLocModule {}
