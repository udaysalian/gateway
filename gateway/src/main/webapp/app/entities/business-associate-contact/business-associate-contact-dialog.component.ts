import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociateContact } from './business-associate-contact.model';
import { BusinessAssociateContactPopupService } from './business-associate-contact-popup.service';
import { BusinessAssociateContactService } from './business-associate-contact.service';
import { BusinessAssociate, BusinessAssociateService } from '../business-associate';
import { Contact, ContactService } from '../contact';
import { BusinessAssociateAddress, BusinessAssociateAddressService } from '../business-associate-address';
@Component({
    selector: 'jhi-business-associate-contact-dialog',
    templateUrl: './business-associate-contact-dialog.component.html'
})
export class BusinessAssociateContactDialogComponent implements OnInit {

    businessAssociateContact: BusinessAssociateContact;
    authorities: any[];
    isSaving: boolean;

    businessassociates: BusinessAssociate[];

    contacts: Contact[];

    mailaddresses: BusinessAssociateAddress[];

    deliveryaddresses: BusinessAssociateAddress[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private businessAssociateContactService: BusinessAssociateContactService,
        private businessAssociateService: BusinessAssociateService,
        private contactService: ContactService,
        private businessAssociateAddressService: BusinessAssociateAddressService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateContact']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.businessAssociateService.query().subscribe(
            (res: Response) => { this.businessassociates = res.json(); }, (res: Response) => this.onError(res.json()));
        this.contactService.query({filter: 'businessassociatecontact-is-null'}).subscribe((res: Response) => {
            if (!this.businessAssociateContact.contact || !this.businessAssociateContact.contact.id) {
                this.contacts = res.json();
            } else {
                this.contactService.find(this.businessAssociateContact.contact.id).subscribe((subRes: Contact) => {
                    this.contacts = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.businessAssociateAddressService.query({filter: 'businessassociatecontact-is-null'}).subscribe((res: Response) => {
            if (!this.businessAssociateContact.mailAddress || !this.businessAssociateContact.mailAddress.id) {
                this.mailaddresses = res.json();
            } else {
                this.businessAssociateAddressService.find(this.businessAssociateContact.mailAddress.id).subscribe((subRes: BusinessAssociateAddress) => {
                    this.mailaddresses = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.businessAssociateAddressService.query({filter: 'businessassociatecontact-is-null'}).subscribe((res: Response) => {
            if (!this.businessAssociateContact.deliveryAddress || !this.businessAssociateContact.deliveryAddress.id) {
                this.deliveryaddresses = res.json();
            } else {
                this.businessAssociateAddressService.find(this.businessAssociateContact.deliveryAddress.id).subscribe((subRes: BusinessAssociateAddress) => {
                    this.deliveryaddresses = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.businessAssociateContact.id !== undefined) {
            this.businessAssociateContactService.update(this.businessAssociateContact)
                .subscribe((res: BusinessAssociateContact) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.businessAssociateContactService.create(this.businessAssociateContact)
                .subscribe((res: BusinessAssociateContact) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: BusinessAssociateContact) {
        this.eventManager.broadcast({ name: 'businessAssociateContactListModification', content: 'OK'});
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

    trackContactById(index: number, item: Contact) {
        return item.id;
    }

    trackBusinessAssociateAddressById(index: number, item: BusinessAssociateAddress) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-business-associate-contact-popup',
    template: ''
})
export class BusinessAssociateContactPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociateContactPopupService: BusinessAssociateContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.businessAssociateContactPopupService
                    .open(BusinessAssociateContactDialogComponent, params['id']);
            } else {
                this.modalRef = this.businessAssociateContactPopupService
                    .open(BusinessAssociateContactDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
