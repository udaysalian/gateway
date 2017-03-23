import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Contract } from './contract.model';
import { ContractPopupService } from './contract-popup.service';
import { ContractService } from './contract.service';
import { RtSched, RtSchedService } from '../rt-sched';
import { ContrLoc, ContrLocService } from '../contr-loc';
import { BusinessAssociate, BusinessAssociateService } from '../business-associate';
@Component({
    selector: 'jhi-contract-dialog',
    templateUrl: './contract-dialog.component.html'
})
export class ContractDialogComponent implements OnInit {

    contract: Contract;
    authorities: any[];
    isSaving: boolean;

    rtscheds: RtSched[];

    contrlocs: ContrLoc[];

    businessassociates: BusinessAssociate[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private contractService: ContractService,
        private rtSchedService: RtSchedService,
        private contrLocService: ContrLocService,
        private businessAssociateService: BusinessAssociateService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['contract']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.rtSchedService.query({filter: 'contract-is-null'}).subscribe((res: Response) => {
            if (!this.contract.rtSched || !this.contract.rtSched.id) {
                this.rtscheds = res.json();
            } else {
                this.rtSchedService.find(this.contract.rtSched.id).subscribe((subRes: RtSched) => {
                    this.rtscheds = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.contrLocService.query().subscribe(
            (res: Response) => { this.contrlocs = res.json(); }, (res: Response) => this.onError(res.json()));
        this.businessAssociateService.query().subscribe(
            (res: Response) => { this.businessassociates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.contract.id !== undefined) {
            this.contractService.update(this.contract)
                .subscribe((res: Contract) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.contractService.create(this.contract)
                .subscribe((res: Contract) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Contract) {
        this.eventManager.broadcast({ name: 'contractListModification', content: 'OK'});
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

    trackRtSchedById(index: number, item: RtSched) {
        return item.id;
    }

    trackContrLocById(index: number, item: ContrLoc) {
        return item.id;
    }

    trackBusinessAssociateById(index: number, item: BusinessAssociate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contract-popup',
    template: ''
})
export class ContractPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private contractPopupService: ContractPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.contractPopupService
                    .open(ContractDialogComponent, params['id']);
            } else {
                this.modalRef = this.contractPopupService
                    .open(ContractDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
