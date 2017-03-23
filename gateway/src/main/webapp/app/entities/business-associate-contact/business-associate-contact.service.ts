import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BusinessAssociateContact } from './business-associate-contact.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class BusinessAssociateContactService {

    private resourceUrl = 'api/business-associate-contacts';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(businessAssociateContact: BusinessAssociateContact): Observable<BusinessAssociateContact> {
        let copy: BusinessAssociateContact = Object.assign({}, businessAssociateContact);
        copy.beginDate = this.dateUtils
            .convertLocalDateToServer(businessAssociateContact.beginDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(businessAssociateContact.endDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(businessAssociateContact: BusinessAssociateContact): Observable<BusinessAssociateContact> {
        let copy: BusinessAssociateContact = Object.assign({}, businessAssociateContact);
        copy.beginDate = this.dateUtils
            .convertLocalDateToServer(businessAssociateContact.beginDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(businessAssociateContact.endDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BusinessAssociateContact> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.beginDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.beginDate);
            jsonResponse.endDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.endDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].beginDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].beginDate);
            jsonResponse[i].endDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].endDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
