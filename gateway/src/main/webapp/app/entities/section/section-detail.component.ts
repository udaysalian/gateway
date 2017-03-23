import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Section } from './section.model';
import { SectionService } from './section.service';

@Component({
    selector: 'jhi-section-detail',
    templateUrl: './section-detail.component.html'
})
export class SectionDetailComponent implements OnInit, OnDestroy {

    section: Section;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sectionService: SectionService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['section']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.sectionService.find(id).subscribe(section => {
            this.section = section;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
