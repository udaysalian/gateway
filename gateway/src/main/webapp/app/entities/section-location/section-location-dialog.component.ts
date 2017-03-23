import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SectionLocation } from './section-location.model';
import { SectionLocationPopupService } from './section-location-popup.service';
import { SectionLocationService } from './section-location.service';
import { Section, SectionService } from '../section';
@Component({
    selector: 'jhi-section-location-dialog',
    templateUrl: './section-location-dialog.component.html'
})
export class SectionLocationDialogComponent implements OnInit {

    sectionLocation: SectionLocation;
    authorities: any[];
    isSaving: boolean;

    sections: Section[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private sectionLocationService: SectionLocationService,
        private sectionService: SectionService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sectionLocation']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sectionService.query().subscribe(
            (res: Response) => { this.sections = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.sectionLocation.id !== undefined) {
            this.sectionLocationService.update(this.sectionLocation)
                .subscribe((res: SectionLocation) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sectionLocationService.create(this.sectionLocation)
                .subscribe((res: SectionLocation) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SectionLocation) {
        this.eventManager.broadcast({ name: 'sectionLocationListModification', content: 'OK'});
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

    trackSectionById(index: number, item: Section) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-section-location-popup',
    template: ''
})
export class SectionLocationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionLocationPopupService: SectionLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.sectionLocationPopupService
                    .open(SectionLocationDialogComponent, params['id']);
            } else {
                this.modalRef = this.sectionLocationPopupService
                    .open(SectionLocationDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
