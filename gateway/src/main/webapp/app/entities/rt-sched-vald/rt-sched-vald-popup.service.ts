import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RtSchedVald } from './rt-sched-vald.model';
import { RtSchedValdService } from './rt-sched-vald.service';
@Injectable()
export class RtSchedValdPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rtSchedValdService: RtSchedValdService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.rtSchedValdService.find(id).subscribe(rtSchedVald => {
                rtSchedVald.updateTimeStamp = this.datePipe.transform(rtSchedVald.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.rtSchedValdModalRef(component, rtSchedVald);
            });
        } else {
            return this.rtSchedValdModalRef(component, new RtSchedVald());
        }
    }

    rtSchedValdModalRef(component: Component, rtSchedVald: RtSchedVald): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rtSchedVald = rtSchedVald;
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
