import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SchedGrp } from './sched-grp.model';
import { SchedGrpService } from './sched-grp.service';

@Component({
    selector: 'jhi-sched-grp-detail',
    templateUrl: './sched-grp-detail.component.html'
})
export class SchedGrpDetailComponent implements OnInit, OnDestroy {

    schedGrp: SchedGrp;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedGrpService: SchedGrpService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['schedGrp']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.schedGrpService.find(id).subscribe(schedGrp => {
            this.schedGrp = schedGrp;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
