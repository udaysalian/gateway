import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SchedGrp } from './sched-grp.model';
import { SchedGrpPopupService } from './sched-grp-popup.service';
import { SchedGrpService } from './sched-grp.service';
import { Section, SectionService } from '../section';
@Component({
    selector: 'jhi-sched-grp-dialog',
    templateUrl: './sched-grp-dialog.component.html'
})
export class SchedGrpDialogComponent implements OnInit {

    schedGrp: SchedGrp;
    authorities: any[];
    isSaving: boolean;

    rcptsections: Section[];

    dlvrysections: Section[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private schedGrpService: SchedGrpService,
        private sectionService: SectionService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedGrp']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sectionService.query({filter: 'schedgrp-is-null'}).subscribe((res: Response) => {
            if (!this.schedGrp.rcptSection || !this.schedGrp.rcptSection.id) {
                this.rcptsections = res.json();
            } else {
                this.sectionService.find(this.schedGrp.rcptSection.id).subscribe((subRes: Section) => {
                    this.rcptsections = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.sectionService.query({filter: 'schedgrp-is-null'}).subscribe((res: Response) => {
            if (!this.schedGrp.dlvrySection || !this.schedGrp.dlvrySection.id) {
                this.dlvrysections = res.json();
            } else {
                this.sectionService.find(this.schedGrp.dlvrySection.id).subscribe((subRes: Section) => {
                    this.dlvrysections = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.schedGrp.id !== undefined) {
            this.schedGrpService.update(this.schedGrp)
                .subscribe((res: SchedGrp) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.schedGrpService.create(this.schedGrp)
                .subscribe((res: SchedGrp) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SchedGrp) {
        this.eventManager.broadcast({ name: 'schedGrpListModification', content: 'OK'});
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

    trackSectionById(index: number, item: Section) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sched-grp-popup',
    template: ''
})
export class SchedGrpPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedGrpPopupService: SchedGrpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.schedGrpPopupService
                    .open(SchedGrpDialogComponent, params['id']);
            } else {
                this.modalRef = this.schedGrpPopupService
                    .open(SchedGrpDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
