import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Nom } from './nom.model';
import { NomPopupService } from './nom-popup.service';
import { NomService } from './nom.service';

@Component({
    selector: 'jhi-nom-delete-dialog',
    templateUrl: './nom-delete-dialog.component.html'
})
export class NomDeleteDialogComponent {

    nom: Nom;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nomService: NomService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['nom']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.nomService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nomListModification',
                content: 'Deleted an nom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nom-delete-popup',
    template: ''
})
export class NomDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nomPopupService: NomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.nomPopupService
                .open(NomDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
