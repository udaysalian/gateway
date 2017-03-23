import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SectionLocation } from './section-location.model';
import { SectionLocationPopupService } from './section-location-popup.service';
import { SectionLocationService } from './section-location.service';

@Component({
    selector: 'jhi-section-location-delete-dialog',
    templateUrl: './section-location-delete-dialog.component.html'
})
export class SectionLocationDeleteDialogComponent {

    sectionLocation: SectionLocation;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sectionLocationService: SectionLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sectionLocation']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.sectionLocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sectionLocationListModification',
                content: 'Deleted an sectionLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-section-location-delete-popup',
    template: ''
})
export class SectionLocationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionLocationPopupService: SectionLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.sectionLocationPopupService
                .open(SectionLocationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
