import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { BusinessAssociateAddress } from './business-associate-address.model';
import { BusinessAssociateAddressService } from './business-associate-address.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-business-associate-address',
    templateUrl: './business-associate-address.component.html'
})
export class BusinessAssociateAddressComponent implements OnInit, OnDestroy {
businessAssociateAddresses: BusinessAssociateAddress[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateAddress']);
    }

    loadAll() {
        this.businessAssociateAddressService.query().subscribe(
            (res: Response) => {
                this.businessAssociateAddresses = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBusinessAssociateAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: BusinessAssociateAddress) {
        return item.id;
    }



    registerChangeInBusinessAssociateAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('businessAssociateAddressListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
