import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SchedNom } from './sched-nom.model';
import { SchedNomPopupService } from './sched-nom-popup.service';
import { SchedNomService } from './sched-nom.service';
import { SchedEvent, SchedEventService } from '../sched-event';
@Component({
    selector: 'jhi-sched-nom-dialog',
    templateUrl: './sched-nom-dialog.component.html'
})
export class SchedNomDialogComponent implements OnInit {

    schedNom: SchedNom;
    authorities: any[];
    isSaving: boolean;

    schedevents: SchedEvent[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private schedNomService: SchedNomService,
        private schedEventService: SchedEventService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedNom']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.schedEventService.query({filter: 'schednom-is-null'}).subscribe((res: Response) => {
            if (!this.schedNom.schedEvent || !this.schedNom.schedEvent.id) {
                this.schedevents = res.json();
            } else {
                this.schedEventService.find(this.schedNom.schedEvent.id).subscribe((subRes: SchedEvent) => {
                    this.schedevents = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.schedNom.id !== undefined) {
            this.schedNomService.update(this.schedNom)
                .subscribe((res: SchedNom) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.schedNomService.create(this.schedNom)
                .subscribe((res: SchedNom) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SchedNom) {
        this.eventManager.broadcast({ name: 'schedNomListModification', content: 'OK'});
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

    trackSchedEventById(index: number, item: SchedEvent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sched-nom-popup',
    template: ''
})
export class SchedNomPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedNomPopupService: SchedNomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.schedNomPopupService
                    .open(SchedNomDialogComponent, params['id']);
            } else {
                this.modalRef = this.schedNomPopupService
                    .open(SchedNomDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
