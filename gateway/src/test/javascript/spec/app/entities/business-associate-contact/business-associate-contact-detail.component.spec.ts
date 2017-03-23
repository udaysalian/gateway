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
import { BusinessAssociateContactDetailComponent } from '../../../../../../main/webapp/app/entities/business-associate-contact/business-associate-contact-detail.component';
import { BusinessAssociateContactService } from '../../../../../../main/webapp/app/entities/business-associate-contact/business-associate-contact.service';
import { BusinessAssociateContact } from '../../../../../../main/webapp/app/entities/business-associate-contact/business-associate-contact.model';

describe('Component Tests', () => {

    describe('BusinessAssociateContact Management Detail Component', () => {
        let comp: BusinessAssociateContactDetailComponent;
        let fixture: ComponentFixture<BusinessAssociateContactDetailComponent>;
        let service: BusinessAssociateContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BusinessAssociateContactDetailComponent],
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
                    BusinessAssociateContactService
                ]
            }).overrideComponent(BusinessAssociateContactDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessAssociateContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessAssociateContactService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BusinessAssociateContact(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.businessAssociateContact).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
