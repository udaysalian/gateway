import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    SchedNomService,
    SchedNomPopupService,
    SchedNomComponent,
    SchedNomDetailComponent,
    SchedNomDialogComponent,
    SchedNomPopupComponent,
    SchedNomDeletePopupComponent,
    SchedNomDeleteDialogComponent,
    schedNomRoute,
    schedNomPopupRoute,
} from './';

let ENTITY_STATES = [
    ...schedNomRoute,
    ...schedNomPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchedNomComponent,
        SchedNomDetailComponent,
        SchedNomDialogComponent,
        SchedNomDeleteDialogComponent,
        SchedNomPopupComponent,
        SchedNomDeletePopupComponent,
    ],
    entryComponents: [
        SchedNomComponent,
        SchedNomDialogComponent,
        SchedNomPopupComponent,
        SchedNomDeleteDialogComponent,
        SchedNomDeletePopupComponent,
    ],
    providers: [
        SchedNomService,
        SchedNomPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraSchedNomModule {}
