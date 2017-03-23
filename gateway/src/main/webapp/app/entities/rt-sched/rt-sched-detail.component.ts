import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { RtSched } from './rt-sched.model';
import { RtSchedService } from './rt-sched.service';

@Component({
    selector: 'jhi-rt-sched-detail',
    templateUrl: './rt-sched-detail.component.html'
})
export class RtSchedDetailComponent implements OnInit, OnDestroy {

    rtSched: RtSched;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rtSchedService: RtSchedService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['rtSched']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.rtSchedService.find(id).subscribe(rtSched => {
            this.rtSched = rtSched;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
