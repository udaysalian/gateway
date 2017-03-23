import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { BusinessAssociateContact } from './business-associate-contact.model';
import { BusinessAssociateContactService } from './business-associate-contact.service';

@Component({
    selector: 'jhi-business-associate-contact-detail',
    templateUrl: './business-associate-contact-detail.component.html'
})
export class BusinessAssociateContactDetailComponent implements OnInit, OnDestroy {

    businessAssociateContact: BusinessAssociateContact;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateContactService: BusinessAssociateContactService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['businessAssociateContact']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.businessAssociateContactService.find(id).subscribe(businessAssociateContact => {
            this.businessAssociateContact = businessAssociateContact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
