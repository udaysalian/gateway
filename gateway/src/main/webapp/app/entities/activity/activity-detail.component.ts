import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Activity } from './activity.model';
import { ActivityService } from './activity.service';

@Component({
    selector: 'jhi-activity-detail',
    templateUrl: './activity-detail.component.html'
})
export class ActivityDetailComponent implements OnInit, OnDestroy {

    activity: Activity;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private activityService: ActivityService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['activity']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.activityService.find(id).subscribe(activity => {
            this.activity = activity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
