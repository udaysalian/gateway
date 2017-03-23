import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RtSched } from './rt-sched.model';
import { RtSchedPopupService } from './rt-sched-popup.service';
import { RtSchedService } from './rt-sched.service';

@Component({
    selector: 'jhi-rt-sched-delete-dialog',
    templateUrl: './rt-sched-delete-dialog.component.html'
})
export class RtSchedDeleteDialogComponent {

    rtSched: RtSched;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rtSchedService: RtSchedService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rtSched']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.rtSchedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rtSchedListModification',
                content: 'Deleted an rtSched'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rt-sched-delete-popup',
    template: ''
})
export class RtSchedDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private rtSchedPopupService: RtSchedPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.rtSchedPopupService
                .open(RtSchedDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
