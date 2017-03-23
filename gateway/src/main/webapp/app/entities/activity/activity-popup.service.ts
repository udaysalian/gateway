import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Activity } from './activity.model';
import { ActivityService } from './activity.service';
@Injectable()
export class ActivityPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private activityService: ActivityService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.activityService.find(id).subscribe(activity => {
                activity.updateTimeStamp = this.datePipe.transform(activity.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.activityModalRef(component, activity);
            });
        } else {
            return this.activityModalRef(component, new Activity());
        }
    }

    activityModalRef(component: Component, activity: Activity): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.activity = activity;
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
