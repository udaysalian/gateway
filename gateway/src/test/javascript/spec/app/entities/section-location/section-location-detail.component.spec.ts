import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SectionLocationDetailComponent } from '../../../../../../main/webapp/app/entities/section-location/section-location-detail.component';
import { SectionLocationService } from '../../../../../../main/webapp/app/entities/section-location/section-location.service';
import { SectionLocation } from '../../../../../../main/webapp/app/entities/section-location/section-location.model';

describe('Component Tests', () => {

    describe('SectionLocation Management Detail Component', () => {
        let comp: SectionLocationDetailComponent;
        let fixture: ComponentFixture<SectionLocationDetailComponent>;
        let service: SectionLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SectionLocationDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    SectionLocationService
                ]
            }).overrideComponent(SectionLocationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SectionLocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SectionLocationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SectionLocation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sectionLocation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
