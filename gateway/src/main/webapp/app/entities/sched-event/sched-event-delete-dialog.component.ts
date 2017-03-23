import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SchedEvent } from './sched-event.model';
import { SchedEventPopupService } from './sched-event-popup.service';
import { SchedEventService } from './sched-event.service';

@Component({
    selector: 'jhi-sched-event-delete-dialog',
    templateUrl: './sched-event-delete-dialog.component.html'
})
export class SchedEventDeleteDialogComponent {

    schedEvent: SchedEvent;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedEventService: SchedEventService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedEvent']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.schedEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schedEventListModification',
                content: 'Deleted an schedEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sched-event-delete-popup',
    template: ''
})
export class SchedEventDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedEventPopupService: SchedEventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.schedEventPopupService
                .open(SchedEventDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
