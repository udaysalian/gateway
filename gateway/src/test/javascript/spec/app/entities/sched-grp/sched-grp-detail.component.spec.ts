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
import { SchedGrpDetailComponent } from '../../../../../../main/webapp/app/entities/sched-grp/sched-grp-detail.component';
import { SchedGrpService } from '../../../../../../main/webapp/app/entities/sched-grp/sched-grp.service';
import { SchedGrp } from '../../../../../../main/webapp/app/entities/sched-grp/sched-grp.model';

describe('Component Tests', () => {

    describe('SchedGrp Management Detail Component', () => {
        let comp: SchedGrpDetailComponent;
        let fixture: ComponentFixture<SchedGrpDetailComponent>;
        let service: SchedGrpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SchedGrpDetailComponent],
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
                    SchedGrpService
                ]
            }).overrideComponent(SchedGrpDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchedGrpDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchedGrpService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchedGrp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schedGrp).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
