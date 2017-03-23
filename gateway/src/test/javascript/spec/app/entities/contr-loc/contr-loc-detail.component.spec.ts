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
import { ContrLocDetailComponent } from '../../../../../../main/webapp/app/entities/contr-loc/contr-loc-detail.component';
import { ContrLocService } from '../../../../../../main/webapp/app/entities/contr-loc/contr-loc.service';
import { ContrLoc } from '../../../../../../main/webapp/app/entities/contr-loc/contr-loc.model';

describe('Component Tests', () => {

    describe('ContrLoc Management Detail Component', () => {
        let comp: ContrLocDetailComponent;
        let fixture: ComponentFixture<ContrLocDetailComponent>;
        let service: ContrLocService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ContrLocDetailComponent],
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
                    ContrLocService
                ]
            }).overrideComponent(ContrLocDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContrLocDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContrLocService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContrLoc(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contrLoc).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
