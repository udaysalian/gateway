import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NetraNomPrtyModule } from './nom-prty/nom-prty.module';
import { NetraActivityModule } from './activity/activity.module';
import { NetraBusinessAssociateAddressModule } from './business-associate-address/business-associate-address.module';
import { NetraBusinessAssociateContactModule } from './business-associate-contact/business-associate-contact.module';
import { NetraContactModule } from './contact/contact.module';
import { NetraContractModule } from './contract/contract.module';
import { NetraContrLocModule } from './contr-loc/contr-loc.module';
import { NetraNomModule } from './nom/nom.module';
import { NetraBusinessAssociateModule } from './business-associate/business-associate.module';
import { NetraRtSchedModule } from './rt-sched/rt-sched.module';
import { NetraRtSchedValdModule } from './rt-sched-vald/rt-sched-vald.module';
import { NetraSchedEventModule } from './sched-event/sched-event.module';
import { NetraSchedGrpModule } from './sched-grp/sched-grp.module';
import { NetraSchedNomModule } from './sched-nom/sched-nom.module';
import { NetraSectionModule } from './section/section.module';
import { NetraSectionLocationModule } from './section-location/section-location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        NetraNomPrtyModule,
        NetraActivityModule,
        NetraBusinessAssociateAddressModule,
        NetraBusinessAssociateContactModule,
        NetraContactModule,
        NetraContractModule,
        NetraContrLocModule,
        NetraNomModule,
        NetraBusinessAssociateModule,
        NetraRtSchedModule,
        NetraRtSchedValdModule,
        NetraSchedEventModule,
        NetraSchedGrpModule,
        NetraSchedNomModule,
        NetraSectionModule,
        NetraSectionLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NetraEntityModule {}
