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
import { RtSchedValdDetailComponent } from '../../../../../../main/webapp/app/entities/rt-sched-vald/rt-sched-vald-detail.component';
import { RtSchedValdService } from '../../../../../../main/webapp/app/entities/rt-sched-vald/rt-sched-vald.service';
import { RtSchedVald } from '../../../../../../main/webapp/app/entities/rt-sched-vald/rt-sched-vald.model';

describe('Component Tests', () => {

    describe('RtSchedVald Management Detail Component', () => {
        let comp: RtSchedValdDetailComponent;
        let fixture: ComponentFixture<RtSchedValdDetailComponent>;
        let service: RtSchedValdService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [RtSchedValdDetailComponent],
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
                    RtSchedValdService
                ]
            }).overrideComponent(RtSchedValdDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RtSchedValdDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RtSchedValdService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RtSchedVald(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rtSchedVald).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
