import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SchedEvent } from './sched-event.model';
import { SchedEventService } from './sched-event.service';
@Injectable()
export class SchedEventPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private schedEventService: SchedEventService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schedEventService.find(id).subscribe(schedEvent => {
                schedEvent.updateTimestamp = this.datePipe.transform(schedEvent.updateTimestamp, 'yyyy-MM-ddThh:mm');
                this.schedEventModalRef(component, schedEvent);
            });
        } else {
            return this.schedEventModalRef(component, new SchedEvent());
        }
    }

    schedEventModalRef(component: Component, schedEvent: SchedEvent): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schedEvent = schedEvent;
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
