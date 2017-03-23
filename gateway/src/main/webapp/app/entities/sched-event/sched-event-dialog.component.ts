import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SchedEvent } from './sched-event.model';
import { SchedEventPopupService } from './sched-event-popup.service';
import { SchedEventService } from './sched-event.service';
import { SchedGrp, SchedGrpService } from '../sched-grp';
@Component({
    selector: 'jhi-sched-event-dialog',
    templateUrl: './sched-event-dialog.component.html'
})
export class SchedEventDialogComponent implements OnInit {

    schedEvent: SchedEvent;
    authorities: any[];
    isSaving: boolean;

    schedgrps: SchedGrp[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private schedEventService: SchedEventService,
        private schedGrpService: SchedGrpService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedEvent']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.schedGrpService.query({filter: 'schedevent-is-null'}).subscribe((res: Response) => {
            if (!this.schedEvent.schedGrp || !this.schedEvent.schedGrp.id) {
                this.schedgrps = res.json();
            } else {
                this.schedGrpService.find(this.schedEvent.schedGrp.id).subscribe((subRes: SchedGrp) => {
                    this.schedgrps = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.schedEvent.id !== undefined) {
            this.schedEventService.update(this.schedEvent)
                .subscribe((res: SchedEvent) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.schedEventService.create(this.schedEvent)
                .subscribe((res: SchedEvent) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SchedEvent) {
        this.eventManager.broadcast({ name: 'schedEventListModification', content: 'OK'});
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

    trackSchedGrpById(index: number, item: SchedGrp) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sched-event-popup',
    template: ''
})
export class SchedEventPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedEventPopupService: SchedEventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.schedEventPopupService
                    .open(SchedEventDialogComponent, params['id']);
            } else {
                this.modalRef = this.schedEventPopupService
                    .open(SchedEventDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
