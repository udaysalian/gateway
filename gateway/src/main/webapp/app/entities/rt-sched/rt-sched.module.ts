import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    RtSchedService,
    RtSchedPopupService,
    RtSchedComponent,
    RtSchedDetailComponent,
    RtSchedDialogComponent,
    RtSchedPopupComponent,
    RtSchedDeletePopupComponent,
    RtSchedDeleteDialogComponent,
    rtSchedRoute,
    rtSchedPopupRoute,
} from './';

let ENTITY_STATES = [
    ...rtSchedRoute,
    ...rtSchedPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RtSchedComponent,
        RtSchedDetailComponent,
        RtSchedDialogComponent,
        RtSchedDeleteDialogComponent,
        RtSchedPopupComponent,
        RtSchedDeletePopupComponent,
    ],
    entryComponents: [
        RtSchedComponent,
        RtSchedDialogComponent,
        RtSchedPopupComponent,
        RtSchedDeleteDialogComponent,
        RtSchedDeletePopupComponent,
    ],
    providers: [
        RtSchedService,
        RtSchedPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraRtSchedModule {}
