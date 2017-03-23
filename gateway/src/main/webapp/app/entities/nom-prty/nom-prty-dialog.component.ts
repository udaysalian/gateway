import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { NomPrty } from './nom-prty.model';
import { NomPrtyPopupService } from './nom-prty-popup.service';
import { NomPrtyService } from './nom-prty.service';
import { Nom, NomService } from '../nom';
@Component({
    selector: 'jhi-nom-prty-dialog',
    templateUrl: './nom-prty-dialog.component.html'
})
export class NomPrtyDialogComponent implements OnInit {

    nomPrty: NomPrty;
    authorities: any[];
    isSaving: boolean;

    noms: Nom[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private nomPrtyService: NomPrtyService,
        private nomService: NomService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['nomPrty']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.nomService.query().subscribe(
            (res: Response) => { this.noms = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.nomPrty.id !== undefined) {
            this.nomPrtyService.update(this.nomPrty)
                .subscribe((res: NomPrty) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.nomPrtyService.create(this.nomPrty)
                .subscribe((res: NomPrty) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: NomPrty) {
        this.eventManager.broadcast({ name: 'nomPrtyListModification', content: 'OK'});
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

    trackNomById(index: number, item: Nom) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-nom-prty-popup',
    template: ''
})
export class NomPrtyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nomPrtyPopupService: NomPrtyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.nomPrtyPopupService
                    .open(NomPrtyDialogComponent, params['id']);
            } else {
                this.modalRef = this.nomPrtyPopupService
                    .open(NomPrtyDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
