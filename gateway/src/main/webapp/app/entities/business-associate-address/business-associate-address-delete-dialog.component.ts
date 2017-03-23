import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociateAddress } from './business-associate-address.model';
import { BusinessAssociateAddressPopupService } from './business-associate-address-popup.service';
import { BusinessAssociateAddressService } from './business-associate-address.service';

@Component({
    selector: 'jhi-business-associate-address-delete-dialog',
    templateUrl: './business-associate-address-delete-dialog.component.html'
})
export class BusinessAssociateAddressDeleteDialogComponent {

    businessAssociateAddress: BusinessAssociateAddress;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateAddress']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.businessAssociateAddressService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'businessAssociateAddressListModification',
                content: 'Deleted an businessAssociateAddress'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-business-associate-address-delete-popup',
    template: ''
})
export class BusinessAssociateAddressDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociateAddressPopupService: BusinessAssociateAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.businessAssociateAddressPopupService
                .open(BusinessAssociateAddressDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
