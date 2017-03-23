import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchedGrp } from './sched-grp.model';
import { SchedGrpService } from './sched-grp.service';
@Injectable()
export class SchedGrpPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private schedGrpService: SchedGrpService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schedGrpService.find(id).subscribe(schedGrp => {
                this.schedGrpModalRef(component, schedGrp);
            });
        } else {
            return this.schedGrpModalRef(component, new SchedGrp());
        }
    }

    schedGrpModalRef(component: Component, schedGrp: SchedGrp): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schedGrp = schedGrp;
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
