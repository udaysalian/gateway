import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RtSchedVald } from './rt-sched-vald.model';
import { RtSchedValdPopupService } from './rt-sched-vald-popup.service';
import { RtSchedValdService } from './rt-sched-vald.service';
import { RtSched, RtSchedService } from '../rt-sched';
@Component({
    selector: 'jhi-rt-sched-vald-dialog',
    templateUrl: './rt-sched-vald-dialog.component.html'
})
export class RtSchedValdDialogComponent implements OnInit {

    rtSchedVald: RtSchedVald;
    authorities: any[];
    isSaving: boolean;

    rtscheds: RtSched[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private rtSchedValdService: RtSchedValdService,
        private rtSchedService: RtSchedService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rtSchedVald']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.rtSchedService.query().subscribe(
            (res: Response) => { this.rtscheds = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.rtSchedVald.id !== undefined) {
            this.rtSchedValdService.update(this.rtSchedVald)
                .subscribe((res: RtSchedVald) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.rtSchedValdService.create(this.rtSchedVald)
                .subscribe((res: RtSchedVald) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: RtSchedVald) {
        this.eventManager.broadcast({ name: 'rtSchedValdListModification', content: 'OK'});
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

    trackRtSchedById(index: number, item: RtSched) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rt-sched-vald-popup',
    template: ''
})
export class RtSchedValdPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private rtSchedValdPopupService: RtSchedValdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.rtSchedValdPopupService
                    .open(RtSchedValdDialogComponent, params['id']);
            } else {
                this.modalRef = this.rtSchedValdPopupService
                    .open(RtSchedValdDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
