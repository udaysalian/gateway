import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ContrLoc } from './contr-loc.model';
import { ContrLocPopupService } from './contr-loc-popup.service';
import { ContrLocService } from './contr-loc.service';

@Component({
    selector: 'jhi-contr-loc-delete-dialog',
    templateUrl: './contr-loc-delete-dialog.component.html'
})
export class ContrLocDeleteDialogComponent {

    contrLoc: ContrLoc;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contrLocService: ContrLocService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['contrLoc']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.contrLocService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contrLocListModification',
                content: 'Deleted an contrLoc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contr-loc-delete-popup',
    template: ''
})
export class ContrLocDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private contrLocPopupService: ContrLocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.contrLocPopupService
                .open(ContrLocDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
