import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    RtSchedValdService,
    RtSchedValdPopupService,
    RtSchedValdComponent,
    RtSchedValdDetailComponent,
    RtSchedValdDialogComponent,
    RtSchedValdPopupComponent,
    RtSchedValdDeletePopupComponent,
    RtSchedValdDeleteDialogComponent,
    rtSchedValdRoute,
    rtSchedValdPopupRoute,
} from './';

let ENTITY_STATES = [
    ...rtSchedValdRoute,
    ...rtSchedValdPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RtSchedValdComponent,
        RtSchedValdDetailComponent,
        RtSchedValdDialogComponent,
        RtSchedValdDeleteDialogComponent,
        RtSchedValdPopupComponent,
        RtSchedValdDeletePopupComponent,
    ],
    entryComponents: [
        RtSchedValdComponent,
        RtSchedValdDialogComponent,
        RtSchedValdPopupComponent,
        RtSchedValdDeleteDialogComponent,
        RtSchedValdDeletePopupComponent,
    ],
    providers: [
        RtSchedValdService,
        RtSchedValdPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraRtSchedValdModule {}
