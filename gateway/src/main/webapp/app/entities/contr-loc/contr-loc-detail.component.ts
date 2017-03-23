import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ContrLoc } from './contr-loc.model';
import { ContrLocService } from './contr-loc.service';

@Component({
    selector: 'jhi-contr-loc-detail',
    templateUrl: './contr-loc-detail.component.html'
})
export class ContrLocDetailComponent implements OnInit, OnDestroy {

    contrLoc: ContrLoc;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contrLocService: ContrLocService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['contrLoc']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.contrLocService.find(id).subscribe(contrLoc => {
            this.contrLoc = contrLoc;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
