import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { NomPrty } from './nom-prty.model';
import { NomPrtyPopupService } from './nom-prty-popup.service';
import { NomPrtyService } from './nom-prty.service';

@Component({
    selector: 'jhi-nom-prty-delete-dialog',
    templateUrl: './nom-prty-delete-dialog.component.html'
})
export class NomPrtyDeleteDialogComponent {

    nomPrty: NomPrty;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nomPrtyService: NomPrtyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['nomPrty']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.nomPrtyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nomPrtyListModification',
                content: 'Deleted an nomPrty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nom-prty-delete-popup',
    template: ''
})
export class NomPrtyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nomPrtyPopupService: NomPrtyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.nomPrtyPopupService
                .open(NomPrtyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
