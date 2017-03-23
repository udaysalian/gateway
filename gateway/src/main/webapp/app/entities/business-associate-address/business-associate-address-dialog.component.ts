import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociateAddress } from './business-associate-address.model';
import { BusinessAssociateAddressPopupService } from './business-associate-address-popup.service';
import { BusinessAssociateAddressService } from './business-associate-address.service';
import { BusinessAssociate, BusinessAssociateService } from '../business-associate';
@Component({
    selector: 'jhi-business-associate-address-dialog',
    templateUrl: './business-associate-address-dialog.component.html'
})
export class BusinessAssociateAddressDialogComponent implements OnInit {

    businessAssociateAddress: BusinessAssociateAddress;
    authorities: any[];
    isSaving: boolean;

    businessassociates: BusinessAssociate[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        private businessAssociateService: BusinessAssociateService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateAddress']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.businessAssociateService.query().subscribe(
            (res: Response) => { this.businessassociates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.businessAssociateAddress.id !== undefined) {
            this.businessAssociateAddressService.update(this.businessAssociateAddress)
                .subscribe((res: BusinessAssociateAddress) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.businessAssociateAddressService.create(this.businessAssociateAddress)
                .subscribe((res: BusinessAssociateAddress) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: BusinessAssociateAddress) {
        this.eventManager.broadcast({ name: 'businessAssociateAddressListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackBusinessAssociateById(index: number, item: BusinessAssociate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-business-associate-address-popup',
    template: ''
})
export class BusinessAssociateAddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociateAddressPopupService: BusinessAssociateAddressPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.businessAssociateAddressPopupService
                    .open(BusinessAssociateAddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.businessAssociateAddressPopupService
                    .open(BusinessAssociateAddressDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
