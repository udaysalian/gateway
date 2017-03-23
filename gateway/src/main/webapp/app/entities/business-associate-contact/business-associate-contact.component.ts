import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { BusinessAssociateContact } from './business-associate-contact.model';
import { BusinessAssociateContactService } from './business-associate-contact.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-business-associate-contact',
    templateUrl: './business-associate-contact.component.html'
})
export class BusinessAssociateContactComponent implements OnInit, OnDestroy {
businessAssociateContacts: BusinessAssociateContact[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateContactService: BusinessAssociateContactService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateContact']);
    }

    loadAll() {
        this.businessAssociateContactService.query().subscribe(
            (res: Response) => {
                this.businessAssociateContacts = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBusinessAssociateContacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: BusinessAssociateContact) {
        return item.id;
    }



    registerChangeInBusinessAssociateContacts() {
        this.eventSubscriber = this.eventManager.subscribe('businessAssociateContactListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
