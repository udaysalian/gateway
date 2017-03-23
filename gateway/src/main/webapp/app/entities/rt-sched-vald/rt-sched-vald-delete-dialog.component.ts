import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RtSchedVald } from './rt-sched-vald.model';
import { RtSchedValdPopupService } from './rt-sched-vald-popup.service';
import { RtSchedValdService } from './rt-sched-vald.service';

@Component({
    selector: 'jhi-rt-sched-vald-delete-dialog',
    templateUrl: './rt-sched-vald-delete-dialog.component.html'
})
export class RtSchedValdDeleteDialogComponent {

    rtSchedVald: RtSchedVald;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rtSchedValdService: RtSchedValdService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rtSchedVald']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.rtSchedValdService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rtSchedValdListModification',
                content: 'Deleted an rtSchedVald'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rt-sched-vald-delete-popup',
    template: ''
})
export class RtSchedValdDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private rtSchedValdPopupService: RtSchedValdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.rtSchedValdPopupService
                .open(RtSchedValdDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
