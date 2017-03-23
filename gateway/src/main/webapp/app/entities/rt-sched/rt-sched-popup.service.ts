import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RtSched } from './rt-sched.model';
import { RtSchedService } from './rt-sched.service';
@Injectable()
export class RtSchedPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rtSchedService: RtSchedService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.rtSchedService.find(id).subscribe(rtSched => {
                rtSched.updateTimeStamp = this.datePipe.transform(rtSched.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.rtSchedModalRef(component, rtSched);
            });
        } else {
            return this.rtSchedModalRef(component, new RtSched());
        }
    }

    rtSchedModalRef(component: Component, rtSched: RtSched): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rtSched = rtSched;
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
