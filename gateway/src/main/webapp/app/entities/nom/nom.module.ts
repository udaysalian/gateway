import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    NomService,
    NomPopupService,
    NomComponent,
    NomDetailComponent,
    NomDialogComponent,
    NomPopupComponent,
    NomDeletePopupComponent,
    NomDeleteDialogComponent,
    nomRoute,
    nomPopupRoute,
} from './';

let ENTITY_STATES = [
    ...nomRoute,
    ...nomPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NomComponent,
        NomDetailComponent,
        NomDialogComponent,
        NomDeleteDialogComponent,
        NomPopupComponent,
        NomDeletePopupComponent,
    ],
    entryComponents: [
        NomComponent,
        NomDialogComponent,
        NomPopupComponent,
        NomDeleteDialogComponent,
        NomDeletePopupComponent,
    ],
    providers: [
        NomService,
        NomPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraNomModule {}
