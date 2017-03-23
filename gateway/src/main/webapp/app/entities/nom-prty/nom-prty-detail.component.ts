import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { NomPrty } from './nom-prty.model';
import { NomPrtyService } from './nom-prty.service';

@Component({
    selector: 'jhi-nom-prty-detail',
    templateUrl: './nom-prty-detail.component.html'
})
export class NomPrtyDetailComponent implements OnInit, OnDestroy {

    nomPrty: NomPrty;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nomPrtyService: NomPrtyService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['nomPrty']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.nomPrtyService.find(id).subscribe(nomPrty => {
            this.nomPrty = nomPrty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
