import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SectionLocation } from './section-location.model';
import { SectionLocationService } from './section-location.service';
@Injectable()
export class SectionLocationPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private sectionLocationService: SectionLocationService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sectionLocationService.find(id).subscribe(sectionLocation => {
                this.sectionLocationModalRef(component, sectionLocation);
            });
        } else {
            return this.sectionLocationModalRef(component, new SectionLocation());
        }
    }

    sectionLocationModalRef(component: Component, sectionLocation: SectionLocation): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sectionLocation = sectionLocation;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
