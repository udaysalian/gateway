import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NetraSharedModule } from '../../shared';

import {
    SectionLocationService,
    SectionLocationPopupService,
    SectionLocationComponent,
    SectionLocationDetailComponent,
    SectionLocationDialogComponent,
    SectionLocationPopupComponent,
    SectionLocationDeletePopupComponent,
    SectionLocationDeleteDialogComponent,
    sectionLocationRoute,
    sectionLocationPopupRoute,
} from './';

let ENTITY_STATES = [
    ...sectionLocationRoute,
    ...sectionLocationPopupRoute,
];

@NgModule({
    imports: [
        NetraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SectionLocationComponent,
        SectionLocationDetailComponent,
        SectionLocationDialogComponent,
        SectionLocationDeleteDialogComponent,
        SectionLocationPopupComponent,
        SectionLocationDeletePopupComponent,
    ],
    entryComponents: [
        SectionLocationComponent,
        SectionLocationDialogComponent,
        SectionLocationPopupComponent,
        SectionLocationDeleteDialogComponent,
        SectionLocationDeletePopupComponent,
    ],
    providers: [
        SectionLocationService,
        SectionLocationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraSectionLocationModule {}
