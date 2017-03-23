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
import { BusinessAssociateAddressDetailComponent } from '../../../../../../main/webapp/app/entities/business-associate-address/business-associate-address-detail.component';
import { BusinessAssociateAddressService } from '../../../../../../main/webapp/app/entities/business-associate-address/business-associate-address.service';
import { BusinessAssociateAddress } from '../../../../../../main/webapp/app/entities/business-associate-address/business-associate-address.model';

describe('Component Tests', () => {

    describe('BusinessAssociateAddress Management Detail Component', () => {
        let comp: BusinessAssociateAddressDetailComponent;
        let fixture: ComponentFixture<BusinessAssociateAddressDetailComponent>;
        let service: BusinessAssociateAddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BusinessAssociateAddressDetailComponent],
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
                    BusinessAssociateAddressService
                ]
            }).overrideComponent(BusinessAssociateAddressDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessAssociateAddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessAssociateAddressService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BusinessAssociateAddress(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.businessAssociateAddress).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
