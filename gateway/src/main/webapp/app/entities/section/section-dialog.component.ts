import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Section } from './section.model';
import { SectionPopupService } from './section-popup.service';
import { SectionService } from './section.service';
import { SectionLocation, SectionLocationService } from '../section-location';
@Component({
    selector: 'jhi-section-dialog',
    templateUrl: './section-dialog.component.html'
})
export class SectionDialogComponent implements OnInit {

    section: Section;
    authorities: any[];
    isSaving: boolean;

    sectionlocations: SectionLocation[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private sectionService: SectionService,
        private sectionLocationService: SectionLocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['section']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sectionLocationService.query().subscribe(
            (res: Response) => { this.sectionlocations = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.section.id !== undefined) {
            this.sectionService.update(this.section)
                .subscribe((res: Section) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sectionService.create(this.section)
                .subscribe((res: Section) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Section) {
        this.eventManager.broadcast({ name: 'sectionListModification', content: 'OK'});
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

    trackSectionLocationById(index: number, item: SectionLocation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-section-popup',
    template: ''
})
export class SectionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionPopupService: SectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.sectionPopupService
                    .open(SectionDialogComponent, params['id']);
            } else {
                this.modalRef = this.sectionPopupService
                    .open(SectionDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
