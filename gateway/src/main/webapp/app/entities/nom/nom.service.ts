import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Nom } from './nom.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class NomService {

    private resourceUrl = 'api/noms';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(nom: Nom): Observable<Nom> {
        let copy: Nom = Object.assign({}, nom);
        copy.gasDate = this.dateUtils
            .convertLocalDateToServer(nom.gasDate);
        copy.updateTimeStamp = this.dateUtils.toDate(nom.updateTimeStamp);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(nom: Nom): Observable<Nom> {
        let copy: Nom = Object.assign({}, nom);
        copy.gasDate = this.dateUtils
            .convertLocalDateToServer(nom.gasDate);

        copy.updateTimeStamp = this.dateUtils.toDate(nom.updateTimeStamp);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Nom> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.gasDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.gasDate);
            jsonResponse.updateTimeStamp = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.updateTimeStamp);
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
            jsonResponse[i].gasDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].gasDate);
            jsonResponse[i].updateTimeStamp = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].updateTimeStamp);
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
