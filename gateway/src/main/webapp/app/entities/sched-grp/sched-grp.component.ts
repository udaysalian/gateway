import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { SchedGrp } from './sched-grp.model';
import { SchedGrpService } from './sched-grp.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-sched-grp',
    templateUrl: './sched-grp.component.html'
})
export class SchedGrpComponent implements OnInit, OnDestroy {
schedGrps: SchedGrp[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedGrpService: SchedGrpService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['schedGrp']);
    }

    loadAll() {
        this.schedGrpService.query().subscribe(
            (res: Response) => {
                this.schedGrps = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSchedGrps();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: SchedGrp) {
        return item.id;
    }



    registerChangeInSchedGrps() {
        this.eventSubscriber = this.eventManager.subscribe('schedGrpListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
