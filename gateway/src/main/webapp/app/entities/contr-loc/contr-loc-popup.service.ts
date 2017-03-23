import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ContrLoc } from './contr-loc.model';
import { ContrLocService } from './contr-loc.service';
@Injectable()
export class ContrLocPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private contrLocService: ContrLocService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.contrLocService.find(id).subscribe(contrLoc => {
                contrLoc.updateTimeStamp = this.datePipe.transform(contrLoc.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.contrLocModalRef(component, contrLoc);
            });
        } else {
            return this.contrLocModalRef(component, new ContrLoc());
        }
    }

    contrLocModalRef(component: Component, contrLoc: ContrLoc): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contrLoc = contrLoc;
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
