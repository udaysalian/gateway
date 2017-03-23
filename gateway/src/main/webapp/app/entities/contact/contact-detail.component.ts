import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Contact } from './contact.model';
import { ContactService } from './contact.service';

@Component({
    selector: 'jhi-contact-detail',
    templateUrl: './contact-detail.component.html'
})
export class ContactDetailComponent implements OnInit, OnDestroy {

    contact: Contact;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contactService: ContactService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['contact']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.contactService.find(id).subscribe(contact => {
            this.contact = contact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
