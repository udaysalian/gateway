import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SchedGrp } from './sched-grp.model';
import { SchedGrpPopupService } from './sched-grp-popup.service';
import { SchedGrpService } from './sched-grp.service';

@Component({
    selector: 'jhi-sched-grp-delete-dialog',
    templateUrl: './sched-grp-delete-dialog.component.html'
})
export class SchedGrpDeleteDialogComponent {

    schedGrp: SchedGrp;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedGrpService: SchedGrpService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedGrp']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.schedGrpService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schedGrpListModification',
                content: 'Deleted an schedGrp'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sched-grp-delete-popup',
    template: ''
})
export class SchedGrpDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedGrpPopupService: SchedGrpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.schedGrpPopupService
                .open(SchedGrpDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
