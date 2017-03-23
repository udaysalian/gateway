import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SectionLocation } from './section-location.model';
import { SectionLocationService } from './section-location.service';

@Component({
    selector: 'jhi-section-location-detail',
    templateUrl: './section-location-detail.component.html'
})
export class SectionLocationDetailComponent implements OnInit, OnDestroy {

    sectionLocation: SectionLocation;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sectionLocationService: SectionLocationService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['sectionLocation']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.sectionLocationService.find(id).subscribe(sectionLocation => {
            this.sectionLocation = sectionLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
