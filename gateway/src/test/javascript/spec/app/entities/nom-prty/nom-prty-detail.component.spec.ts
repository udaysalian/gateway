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
import { NomPrtyDetailComponent } from '../../../../../../main/webapp/app/entities/nom-prty/nom-prty-detail.component';
import { NomPrtyService } from '../../../../../../main/webapp/app/entities/nom-prty/nom-prty.service';
import { NomPrty } from '../../../../../../main/webapp/app/entities/nom-prty/nom-prty.model';

describe('Component Tests', () => {

    describe('NomPrty Management Detail Component', () => {
        let comp: NomPrtyDetailComponent;
        let fixture: ComponentFixture<NomPrtyDetailComponent>;
        let service: NomPrtyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [NomPrtyDetailComponent],
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
                    NomPrtyService
                ]
            }).overrideComponent(NomPrtyDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NomPrtyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NomPrtyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new NomPrty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.nomPrty).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
