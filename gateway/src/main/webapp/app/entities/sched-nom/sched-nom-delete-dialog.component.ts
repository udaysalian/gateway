import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SchedNom } from './sched-nom.model';
import { SchedNomPopupService } from './sched-nom-popup.service';
import { SchedNomService } from './sched-nom.service';

@Component({
    selector: 'jhi-sched-nom-delete-dialog',
    templateUrl: './sched-nom-delete-dialog.component.html'
})
export class SchedNomDeleteDialogComponent {

    schedNom: SchedNom;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedNomService: SchedNomService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedNom']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.schedNomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'schedNomListModification',
                content: 'Deleted an schedNom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sched-nom-delete-popup',
    template: ''
})
export class SchedNomDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private schedNomPopupService: SchedNomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.schedNomPopupService
                .open(SchedNomDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
