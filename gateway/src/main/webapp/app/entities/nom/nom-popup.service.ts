import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Nom } from './nom.model';
import { NomService } from './nom.service';
@Injectable()
export class NomPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private nomService: NomService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.nomService.find(id).subscribe(nom => {
                if (nom.gasDate) {
                    nom.gasDate = {
                        year: nom.gasDate.getFullYear(),
                        month: nom.gasDate.getMonth() + 1,
                        day: nom.gasDate.getDate()
                    };
                }
                nom.updateTimeStamp = this.datePipe.transform(nom.updateTimeStamp, 'yyyy-MM-ddThh:mm');
                this.nomModalRef(component, nom);
            });
        } else {
            return this.nomModalRef(component, new Nom());
        }
    }

    nomModalRef(component: Component, nom: Nom): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nom = nom;
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
