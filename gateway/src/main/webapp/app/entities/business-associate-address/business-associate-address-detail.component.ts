import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { BusinessAssociateAddress } from './business-associate-address.model';
import { BusinessAssociateAddressService } from './business-associate-address.service';

@Component({
    selector: 'jhi-business-associate-address-detail',
    templateUrl: './business-associate-address-detail.component.html'
})
export class BusinessAssociateAddressDetailComponent implements OnInit, OnDestroy {

    businessAssociateAddress: BusinessAssociateAddress;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateAddress']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.businessAssociateAddressService.find(id).subscribe(businessAssociateAddress => {
            this.businessAssociateAddress = businessAssociateAddress;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
