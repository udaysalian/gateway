import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SchedNom } from './sched-nom.model';
import { SchedNomService } from './sched-nom.service';
@Injectable()
export class SchedNomPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private schedNomService: SchedNomService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.schedNomService.find(id).subscribe(schedNom => {
                if (schedNom.gasDate) {
                    schedNom.gasDate = {
                        year: schedNom.gasDate.getFullYear(),
                        month: schedNom.gasDate.getMonth() + 1,
                        day: schedNom.gasDate.getDate()
                    };
                }
                schedNom.updateTimeStamp = this.datePipe.transform(schedNom.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.schedNomModalRef(component, schedNom);
            });
        } else {
            return this.schedNomModalRef(component, new SchedNom());
        }
    }

    schedNomModalRef(component: Component, schedNom: SchedNom): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schedNom = schedNom;
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
