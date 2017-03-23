import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BusinessAssociate } from './business-associate.model';
import { BusinessAssociateService } from './business-associate.service';
@Injectable()
export class BusinessAssociatePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private businessAssociateService: BusinessAssociateService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.businessAssociateService.find(id).subscribe(businessAssociate => {
                this.businessAssociateModalRef(component, businessAssociate);
            });
        } else {
            return this.businessAssociateModalRef(component, new BusinessAssociate());
        }
    }

    businessAssociateModalRef(component: Component, businessAssociate: BusinessAssociate): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.businessAssociate = businessAssociate;
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
