import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { RtSchedVald } from './rt-sched-vald.model';
import { RtSchedValdService } from './rt-sched-vald.service';

@Component({
    selector: 'jhi-rt-sched-vald-detail',
    templateUrl: './rt-sched-vald-detail.component.html'
})
export class RtSchedValdDetailComponent implements OnInit, OnDestroy {

    rtSchedVald: RtSchedVald;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rtSchedValdService: RtSchedValdService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['rtSchedVald']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.rtSchedValdService.find(id).subscribe(rtSchedVald => {
            this.rtSchedVald = rtSchedVald;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
