import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RtSched } from './rt-sched.model';
import { RtSchedPopupService } from './rt-sched-popup.service';
import { RtSchedService } from './rt-sched.service';
import { RtSchedVald, RtSchedValdService } from '../rt-sched-vald';
@Component({
    selector: 'jhi-rt-sched-dialog',
    templateUrl: './rt-sched-dialog.component.html'
})
export class RtSchedDialogComponent implements OnInit {

    rtSched: RtSched;
    authorities: any[];
    isSaving: boolean;

    rtschedvalds: RtSchedVald[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private rtSchedService: RtSchedService,
        private rtSchedValdService: RtSchedValdService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rtSched']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.rtSchedValdService.query().subscribe(
            (res: Response) => { this.rtschedvalds = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.rtSched.id !== undefined) {
            this.rtSchedService.update(this.rtSched)
                .subscribe((res: RtSched) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.rtSchedService.create(this.rtSched)
                .subscribe((res: RtSched) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: RtSched) {
        this.eventManager.broadcast({ name: 'rtSchedListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackRtSchedValdById(index: number, item: RtSchedVald) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rt-sched-popup',
    template: ''
})
export class RtSchedPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private rtSchedPopupService: RtSchedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.rtSchedPopupService
                    .open(RtSchedDialogComponent, params['id']);
            } else {
                this.modalRef = this.rtSchedPopupService
                    .open(RtSchedDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
