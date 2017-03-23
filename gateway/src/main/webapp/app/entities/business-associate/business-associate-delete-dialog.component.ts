import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociate } from './business-associate.model';
import { BusinessAssociatePopupService } from './business-associate-popup.service';
import { BusinessAssociateService } from './business-associate.service';

@Component({
    selector: 'jhi-business-associate-delete-dialog',
    templateUrl: './business-associate-delete-dialog.component.html'
})
export class BusinessAssociateDeleteDialogComponent {

    businessAssociate: BusinessAssociate;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateService: BusinessAssociateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociate']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.businessAssociateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'businessAssociateListModification',
                content: 'Deleted an businessAssociate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-business-associate-delete-popup',
    template: ''
})
export class BusinessAssociateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociatePopupService: BusinessAssociatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.businessAssociatePopupService
                .open(BusinessAssociateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
