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
import { RtSchedDetailComponent } from '../../../../../../main/webapp/app/entities/rt-sched/rt-sched-detail.component';
import { RtSchedService } from '../../../../../../main/webapp/app/entities/rt-sched/rt-sched.service';
import { RtSched } from '../../../../../../main/webapp/app/entities/rt-sched/rt-sched.model';

describe('Component Tests', () => {

    describe('RtSched Management Detail Component', () => {
        let comp: RtSchedDetailComponent;
        let fixture: ComponentFixture<RtSchedDetailComponent>;
        let service: RtSchedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [RtSchedDetailComponent],
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
                    RtSchedService
                ]
            }).overrideComponent(RtSchedDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RtSchedDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RtSchedService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RtSched(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rtSched).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
