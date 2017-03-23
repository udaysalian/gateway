import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BusinessAssociateAddress } from './business-associate-address.model';
import { BusinessAssociateAddressService } from './business-associate-address.service';
@Injectable()
export class BusinessAssociateAddressPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private businessAssociateAddressService: BusinessAssociateAddressService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.businessAssociateAddressService.find(id).subscribe(businessAssociateAddress => {
                this.businessAssociateAddressModalRef(component, businessAssociateAddress);
            });
        } else {
            return this.businessAssociateAddressModalRef(component, new BusinessAssociateAddress());
        }
    }

    businessAssociateAddressModalRef(component: Component, businessAssociateAddress: BusinessAssociateAddress): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.businessAssociateAddress = businessAssociateAddress;
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
