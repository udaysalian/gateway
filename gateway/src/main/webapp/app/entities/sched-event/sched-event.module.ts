import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    SchedEventService,
    SchedEventPopupService,
    SchedEventComponent,
    SchedEventDetailComponent,
    SchedEventDialogComponent,
    SchedEventPopupComponent,
    SchedEventDeletePopupComponent,
    SchedEventDeleteDialogComponent,
    schedEventRoute,
    schedEventPopupRoute,
} from './';

let ENTITY_STATES = [
    ...schedEventRoute,
    ...schedEventPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchedEventComponent,
        SchedEventDetailComponent,
        SchedEventDialogComponent,
        SchedEventDeleteDialogComponent,
        SchedEventPopupComponent,
        SchedEventDeletePopupComponent,
    ],
    entryComponents: [
        SchedEventComponent,
        SchedEventDialogComponent,
        SchedEventPopupComponent,
        SchedEventDeleteDialogComponent,
        SchedEventDeletePopupComponent,
    ],
    providers: [
        SchedEventService,
        SchedEventPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraSchedEventModule {}
