import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    NomPrtyService,
    NomPrtyPopupService,
    NomPrtyComponent,
    NomPrtyDetailComponent,
    NomPrtyDialogComponent,
    NomPrtyPopupComponent,
    NomPrtyDeletePopupComponent,
    NomPrtyDeleteDialogComponent,
    nomPrtyRoute,
    nomPrtyPopupRoute,
} from './';

let ENTITY_STATES = [
    ...nomPrtyRoute,
    ...nomPrtyPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NomPrtyComponent,
        NomPrtyDetailComponent,
        NomPrtyDialogComponent,
        NomPrtyDeleteDialogComponent,
        NomPrtyPopupComponent,
        NomPrtyDeletePopupComponent,
    ],
    entryComponents: [
        NomPrtyComponent,
        NomPrtyDialogComponent,
        NomPrtyPopupComponent,
        NomPrtyDeleteDialogComponent,
        NomPrtyDeletePopupComponent,
    ],
    providers: [
        NomPrtyService,
        NomPrtyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraNomPrtyModule {}
