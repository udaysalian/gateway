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
import { SchedEventDetailComponent } from '../../../../../../main/webapp/app/entities/sched-event/sched-event-detail.component';
import { SchedEventService } from '../../../../../../main/webapp/app/entities/sched-event/sched-event.service';
import { SchedEvent } from '../../../../../../main/webapp/app/entities/sched-event/sched-event.model';

describe('Component Tests', () => {

    describe('SchedEvent Management Detail Component', () => {
        let comp: SchedEventDetailComponent;
        let fixture: ComponentFixture<SchedEventDetailComponent>;
        let service: SchedEventService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SchedEventDetailComponent],
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
                    SchedEventService
                ]
            }).overrideComponent(SchedEventDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchedEventDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchedEventService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchedEvent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schedEvent).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
