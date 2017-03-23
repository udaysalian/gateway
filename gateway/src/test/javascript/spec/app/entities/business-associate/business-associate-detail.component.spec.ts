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
import { BusinessAssociateDetailComponent } from '../../../../../../main/webapp/app/entities/business-associate/business-associate-detail.component';
import { BusinessAssociateService } from '../../../../../../main/webapp/app/entities/business-associate/business-associate.service';
import { BusinessAssociate } from '../../../../../../main/webapp/app/entities/business-associate/business-associate.model';

describe('Component Tests', () => {

    describe('BusinessAssociate Management Detail Component', () => {
        let comp: BusinessAssociateDetailComponent;
        let fixture: ComponentFixture<BusinessAssociateDetailComponent>;
        let service: BusinessAssociateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BusinessAssociateDetailComponent],
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
                    BusinessAssociateService
                ]
            }).overrideComponent(BusinessAssociateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessAssociateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessAssociateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BusinessAssociate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.businessAssociate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
