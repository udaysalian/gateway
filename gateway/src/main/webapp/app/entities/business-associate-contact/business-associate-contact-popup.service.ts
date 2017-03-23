import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BusinessAssociateContact } from './business-associate-contact.model';
import { BusinessAssociateContactService } from './business-associate-contact.service';
@Injectable()
export class BusinessAssociateContactPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private businessAssociateContactService: BusinessAssociateContactService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.businessAssociateContactService.find(id).subscribe(businessAssociateContact => {
                if (businessAssociateContact.beginDate) {
                    businessAssociateContact.beginDate = {
                        year: businessAssociateContact.beginDate.getFullYear(),
                        month: businessAssociateContact.beginDate.getMonth() + 1,
                        day: businessAssociateContact.beginDate.getDate()
                    };
                }
                if (businessAssociateContact.endDate) {
                    businessAssociateContact.endDate = {
                        year: businessAssociateContact.endDate.getFullYear(),
                        month: businessAssociateContact.endDate.getMonth() + 1,
                        day: businessAssociateContact.endDate.getDate()
                    };
                }
                this.businessAssociateContactModalRef(component, businessAssociateContact);
            });
        } else {
            return this.businessAssociateContactModalRef(component, new BusinessAssociateContact());
        }
    }

    businessAssociateContactModalRef(component: Component, businessAssociateContact: BusinessAssociateContact): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.businessAssociateContact = businessAssociateContact;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
