import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ventas } from './ventas.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VentasService {

    private resourceUrl = SERVER_API_URL + 'api/ventas';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ventas: Ventas): Observable<Ventas> {
        const copy = this.convert(ventas);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(ventas: Ventas): Observable<Ventas> {
        const copy = this.convert(ventas);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Ventas> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fecha = this.dateUtils
            .convertLocalDateFromServer(entity.fecha);
    }

    private convert(ventas: Ventas): Ventas {
        const copy: Ventas = Object.assign({}, ventas);
        copy.fecha = this.dateUtils
            .convertLocalDateToServer(ventas.fecha);
        return copy;
    }
}
