import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Nom } from './nom.model';
import { NomPopupService } from './nom-popup.service';
import { NomService } from './nom.service';
import { Activity, ActivityService } from '../activity';
import { Contract, ContractService } from '../contract';
import { NomPrty, NomPrtyService } from '../nom-prty';
@Component({
    selector: 'jhi-nom-dialog',
    templateUrl: './nom-dialog.component.html'
})
export class NomDialogComponent implements OnInit {

    nom: Nom;
    authorities: any[];
    isSaving: boolean;

    activities: Activity[];

    contrs: Contract[];

    nomprties: NomPrty[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private nomService: NomService,
        private activityService: ActivityService,
        private contractService: ContractService,
        private nomPrtyService: NomPrtyService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['nom']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.activityService.query({filter: 'nom-is-null'}).subscribe((res: Response) => {
            if (!this.nom.activity || !this.nom.activity.id) {
                this.activities = res.json();
            } else {
                this.activityService.find(this.nom.activity.id).subscribe((subRes: Activity) => {
                    this.activities = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.contractService.query({filter: 'nom-is-null'}).subscribe((res: Response) => {
            if (!this.nom.contr || !this.nom.contr.id) {
                this.contrs = res.json();
            } else {
                this.contractService.find(this.nom.contr.id).subscribe((subRes: Contract) => {
                    this.contrs = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.nomPrtyService.query().subscribe(
            (res: Response) => { this.nomprties = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.nom.id !== undefined) {
            this.nomService.update(this.nom)
                .subscribe((res: Nom) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.nomService.create(this.nom)
                .subscribe((res: Nom) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Nom) {
        this.eventManager.broadcast({ name: 'nomListModification', content: 'OK'});
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

    trackActivityById(index: number, item: Activity) {
        return item.id;
    }

    trackContractById(index: number, item: Contract) {
        return item.id;
    }

    trackNomPrtyById(index: number, item: NomPrty) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-nom-popup',
    template: ''
})
export class NomPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nomPopupService: NomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.nomPopupService
                    .open(NomDialogComponent, params['id']);
            } else {
                this.modalRef = this.nomPopupService
                    .open(NomDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
