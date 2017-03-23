import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { NomPrty } from './nom-prty.model';
import { NomPrtyService } from './nom-prty.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-nom-prty',
    templateUrl: './nom-prty.component.html'
})
export class NomPrtyComponent implements OnInit, OnDestroy {
nomPrties: NomPrty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nomPrtyService: NomPrtyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['nomPrty']);
    }

    loadAll() {
        this.nomPrtyService.query().subscribe(
            (res: Response) => {
                this.nomPrties = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNomPrties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: NomPrty) {
        return item.id;
    }



    registerChangeInNomPrties() {
        this.eventSubscriber = this.eventManager.subscribe('nomPrtyListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
