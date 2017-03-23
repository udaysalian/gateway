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
import { NomDetailComponent } from '../../../../../../main/webapp/app/entities/nom/nom-detail.component';
import { NomService } from '../../../../../../main/webapp/app/entities/nom/nom.service';
import { Nom } from '../../../../../../main/webapp/app/entities/nom/nom.model';

describe('Component Tests', () => {

    describe('Nom Management Detail Component', () => {
        let comp: NomDetailComponent;
        let fixture: ComponentFixture<NomDetailComponent>;
        let service: NomService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [NomDetailComponent],
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
                    NomService
                ]
            }).overrideComponent(NomDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NomDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NomService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Nom(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.nom).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
