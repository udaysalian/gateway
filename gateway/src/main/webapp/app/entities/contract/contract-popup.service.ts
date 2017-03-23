import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Contract } from './contract.model';
import { ContractService } from './contract.service';
@Injectable()
export class ContractPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private contractService: ContractService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.contractService.find(id).subscribe(contract => {
                contract.updateTimeStamp = this.datePipe.transform(contract.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.contractModalRef(component, contract);
            });
        } else {
            return this.contractModalRef(component, new Contract());
        }
    }

    contractModalRef(component: Component, contract: Contract): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contract = contract;
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
