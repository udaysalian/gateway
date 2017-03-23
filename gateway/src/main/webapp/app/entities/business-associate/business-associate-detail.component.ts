import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { BusinessAssociate } from './business-associate.model';
import { BusinessAssociateService } from './business-associate.service';

@Component({
    selector: 'jhi-business-associate-detail',
    templateUrl: './business-associate-detail.component.html'
})
export class BusinessAssociateDetailComponent implements OnInit, OnDestroy {

    businessAssociate: BusinessAssociate;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private businessAssociateService: BusinessAssociateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['businessAssociate']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.businessAssociateService.find(id).subscribe(businessAssociate => {
            this.businessAssociate = businessAssociate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
