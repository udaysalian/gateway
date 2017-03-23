import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { RtSchedVald } from './rt-sched-vald.model';
import { RtSchedValdService } from './rt-sched-vald.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-rt-sched-vald',
    templateUrl: './rt-sched-vald.component.html'
})
export class RtSchedValdComponent implements OnInit, OnDestroy {
rtSchedValds: RtSchedVald[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rtSchedValdService: RtSchedValdService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['rtSchedVald']);
    }

    loadAll() {
        this.rtSchedValdService.query().subscribe(
            (res: Response) => {
                this.rtSchedValds = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRtSchedValds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: RtSchedVald) {
        return item.id;
    }



    registerChangeInRtSchedValds() {
        this.eventSubscriber = this.eventManager.subscribe('rtSchedValdListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
