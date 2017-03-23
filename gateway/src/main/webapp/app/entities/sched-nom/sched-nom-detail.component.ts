import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SchedNom } from './sched-nom.model';
import { SchedNomService } from './sched-nom.service';

@Component({
    selector: 'jhi-sched-nom-detail',
    templateUrl: './sched-nom-detail.component.html'
})
export class SchedNomDetailComponent implements OnInit, OnDestroy {

    schedNom: SchedNom;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedNomService: SchedNomService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['schedNom']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.schedNomService.find(id).subscribe(schedNom => {
            this.schedNom = schedNom;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
