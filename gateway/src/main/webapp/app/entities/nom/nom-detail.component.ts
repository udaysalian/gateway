import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Nom } from './nom.model';
import { NomService } from './nom.service';

@Component({
    selector: 'jhi-nom-detail',
    templateUrl: './nom-detail.component.html'
})
export class NomDetailComponent implements OnInit, OnDestroy {

    nom: Nom;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nomService: NomService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['nom']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.nomService.find(id).subscribe(nom => {
            this.nom = nom;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
