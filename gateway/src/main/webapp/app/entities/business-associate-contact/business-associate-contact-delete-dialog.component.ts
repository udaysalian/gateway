import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { BusinessAssociateContact } from './business-associate-contact.model';
import { BusinessAssociateContactPopupService } from './business-associate-contact-popup.service';
import { BusinessAssociateContactService } from './business-associate-contact.service';

@Component({
    selector: 'jhi-business-associate-contact-delete-dialog',
    templateUrl: './business-associate-contact-delete-dialog.component.html'
})
export class BusinessAssociateContactDeleteDialogComponent {

    businessAssociateContact: BusinessAssociateContact;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateContactService: BusinessAssociateContactService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateContact']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.businessAssociateContactService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'businessAssociateContactListModification',
                content: 'Deleted an businessAssociateContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-business-associate-contact-delete-popup',
    template: ''
})
export class BusinessAssociateContactDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private businessAssociateContactPopupService: BusinessAssociateContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.businessAssociateContactPopupService
                .open(BusinessAssociateContactDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
