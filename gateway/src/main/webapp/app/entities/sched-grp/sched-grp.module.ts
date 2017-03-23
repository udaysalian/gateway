import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    SchedGrpService,
    SchedGrpPopupService,
    SchedGrpComponent,
    SchedGrpDetailComponent,
    SchedGrpDialogComponent,
    SchedGrpPopupComponent,
    SchedGrpDeletePopupComponent,
    SchedGrpDeleteDialogComponent,
    schedGrpRoute,
    schedGrpPopupRoute,
} from './';

let ENTITY_STATES = [
    ...schedGrpRoute,
    ...schedGrpPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchedGrpComponent,
        SchedGrpDetailComponent,
        SchedGrpDialogComponent,
        SchedGrpDeleteDialogComponent,
        SchedGrpPopupComponent,
        SchedGrpDeletePopupComponent,
    ],
    entryComponents: [
        SchedGrpComponent,
        SchedGrpDialogComponent,
        SchedGrpPopupComponent,
        SchedGrpDeleteDialogComponent,
        SchedGrpDeletePopupComponent,
    ],
    providers: [
        SchedGrpService,
        SchedGrpPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraSchedGrpModule {}
