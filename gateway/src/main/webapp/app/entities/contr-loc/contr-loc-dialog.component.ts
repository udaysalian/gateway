import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ContrLoc } from './contr-loc.model';
import { ContrLocPopupService } from './contr-loc-popup.service';
import { ContrLocService } from './contr-loc.service';
import { Contract, ContractService } from '../contract';
@Component({
    selector: 'jhi-contr-loc-dialog',
    templateUrl: './contr-loc-dialog.component.html'
})
export class ContrLocDialogComponent implements OnInit {

    contrLoc: ContrLoc;
    authorities: any[];
    isSaving: boolean;

    contracts: Contract[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private contrLocService: ContrLocService,
        private contractService: ContractService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['contrLoc']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.contractService.query().subscribe(
            (res: Response) => { this.contracts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.contrLoc.id !== undefined) {
            this.contrLocService.update(this.contrLoc)
                .subscribe((res: ContrLoc) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.contrLocService.create(this.contrLoc)
                .subscribe((res: ContrLoc) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ContrLoc) {
        this.eventManager.broadcast({ name: 'contrLocListModification', content: 'OK'});
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

    trackContractById(index: number, item: Contract) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contr-loc-popup',
    template: ''
})
export class ContrLocPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private contrLocPopupService: ContrLocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.contrLocPopupService
                    .open(ContrLocDialogComponent, params['id']);
            } else {
                this.modalRef = this.contrLocPopupService
                    .open(ContrLocDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
