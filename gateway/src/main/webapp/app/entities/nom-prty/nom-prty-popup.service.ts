import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { NomPrty } from './nom-prty.model';
import { NomPrtyService } from './nom-prty.service';
@Injectable()
export class NomPrtyPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private nomPrtyService: NomPrtyService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.nomPrtyService.find(id).subscribe(nomPrty => {
                if (nomPrty.gasDate) {
                    nomPrty.gasDate = {
                        year: nomPrty.gasDate.getFullYear(),
                        month: nomPrty.gasDate.getMonth() + 1,
                        day: nomPrty.gasDate.getDate()
                    };
                }
                nomPrty.updateTimeStamp = this.datePipe.transform(nomPrty.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.nomPrtyModalRef(component, nomPrty);
            });
        } else {
            return this.nomPrtyModalRef(component, new NomPrty());
        }
    }

    nomPrtyModalRef(component: Component, nomPrty: NomPrty): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nomPrty = nomPrty;
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
