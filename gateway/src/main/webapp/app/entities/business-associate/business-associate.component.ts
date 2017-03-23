import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { BusinessAssociate } from './business-associate.model';
import { BusinessAssociateService } from './business-associate.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-business-associate',
    templateUrl: './business-associate.component.html'
})
export class BusinessAssociateComponent implements OnInit, OnDestroy {
businessAssociates: BusinessAssociate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateService: BusinessAssociateService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['businessAssociate']);
    }

    loadAll() {
        this.businessAssociateService.query().subscribe(
            (res: Response) => {
                this.businessAssociates = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBusinessAssociates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: BusinessAssociate) {
        return item.id;
    }



    registerChangeInBusinessAssociates() {
        this.eventSubscriber = this.eventManager.subscribe('businessAssociateListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
