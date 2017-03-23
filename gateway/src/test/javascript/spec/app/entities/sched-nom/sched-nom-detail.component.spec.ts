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
import { SchedNomDetailComponent } from '../../../../../../main/webapp/app/entities/sched-nom/sched-nom-detail.component';
import { SchedNomService } from '../../../../../../main/webapp/app/entities/sched-nom/sched-nom.service';
import { SchedNom } from '../../../../../../main/webapp/app/entities/sched-nom/sched-nom.model';

describe('Component Tests', () => {

    describe('SchedNom Management Detail Component', () => {
        let comp: SchedNomDetailComponent;
        let fixture: ComponentFixture<SchedNomDetailComponent>;
        let service: SchedNomService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SchedNomDetailComponent],
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
                    SchedNomService
                ]
            }).overrideComponent(SchedNomDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchedNomDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchedNomService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SchedNom(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schedNom).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
