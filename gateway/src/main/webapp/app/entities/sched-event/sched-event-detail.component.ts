import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SchedEvent } from './sched-event.model';
import { SchedEventService } from './sched-event.service';

@Component({
    selector: 'jhi-sched-event-detail',
    templateUrl: './sched-event-detail.component.html'
})
export class SchedEventDetailComponent implements OnInit, OnDestroy {

    schedEvent: SchedEvent;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private schedEventService: SchedEventService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['schedEvent']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.schedEventService.find(id).subscribe(schedEvent => {
            this.schedEvent = schedEvent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
