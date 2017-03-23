import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociate } from './business-associate.model';
import { BusinessAssociatePopupService } from './business-associate-popup.service';
import { BusinessAssociateService } from './business-associate.service';
import { BusinessAssociateContact, BusinessAssociateContactService } from '../business-associate-contact';
import { BusinessAssociateAddress, BusinessAssociateAddressService } from '../business-associate-address';
@Component({
    selector: 'jhi-business-associate-dialog',
    templateUrl: './business-associate-dialog.component.html'
})
export class BusinessAssociateDialogComponent implements OnInit {

    businessAssociate: BusinessAssociate;
    authorities: any[];
    isSaving: boolean;

    businessassociatecontacts: BusinessAssociateContact[];

    businessassociateaddresses: BusinessAssociateAddress[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private businessAssociateService: BusinessAssociateService,
        private businessAssociateContactService: BusinessAssociateContactService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociate']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.businessAssociateContactService.query().subscribe(
            (res: Response) => { this.businessassociatecontacts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.businessAssociateAddressService.query().subscribe(
            (res: Response) => { this.businessassociateaddresses = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.businessAssociate.id !== undefined) {
            this.businessAssociateService.update(this.businessAssociate)
                .subscribe((res: BusinessAssociate) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.businessAssociateService.create(this.businessAssociate)
                .subscribe((res: BusinessAssociate) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: BusinessAssociate) {
        this.eventManager.broadcast({ name: 'businessAssociateListModification', content: 'OK'});
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

    trackBusinessAssociateContactById(index: number, item: BusinessAssociateContact) {
        return item.id;
    }

    trackBusinessAssociateAddressById(index: number, item: BusinessAssociateAddress) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-business-associate-popup',
    template: ''
})
export class BusinessAssociatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociatePopupService: BusinessAssociatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.businessAssociatePopupService
                    .open(BusinessAssociateDialogComponent, params['id']);
            } else {
                this.modalRef = this.businessAssociatePopupService
                    .open(BusinessAssociateDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
