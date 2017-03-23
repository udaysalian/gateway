import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SchedNom } from './sched-nom.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class SchedNomService {

    private resourceUrl = 'api/sched-noms';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(schedNom: SchedNom): Observable<SchedNom> {
        let copy: SchedNom = Object.assign({}, schedNom);
        copy.gasDate = this.dateUtils
            .convertLocalDateToServer(schedNom.gasDate);
        copy.updateTimeStamp = this.dateUtils.toDate(schedNom.updateTimeStamp);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(schedNom: SchedNom): Observable<SchedNom> {
        let copy: SchedNom = Object.assign({}, schedNom);
        copy.gasDate = this.dateUtils
            .convertLocalDateToServer(schedNom.gasDate);

        copy.updateTimeStamp = this.dateUtils.toDate(schedNom.updateTimeStamp);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SchedNom> {
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
