import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Historial } from './historial.model';
import { HistorialPopupService } from './historial-popup.service';
import { HistorialService } from './historial.service';
import { Producto, ProductoService } from '../producto';
import { Ventas, VentasService } from '../ventas';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-historial-dialog',
    templateUrl: './historial-dialog.component.html'
})
export class HistorialDialogComponent implements OnInit {

    historial: Historial;
    isSaving: boolean;

    productos: Producto[];

    ventas: Ventas[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private historialService: HistorialService,
        private productoService: ProductoService,
        private ventasService: VentasService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productoService.query()
            .subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ventasService.query()
            .subscribe((res: ResponseWrapper) => { this.ventas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.historial.id !== undefined) {
            this.subscribeToSaveResponse(
                this.historialService.update(this.historial));
        } else {
            this.subscribeToSaveResponse(
                this.historialService.create(this.historial));
        }
    }

    private subscribeToSaveResponse(result: Observable<Historial>) {
        result.subscribe((res: Historial) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Historial) {
        this.eventManager.broadcast({ name: 'historialListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackProductoById(index: number, item: Producto) {
        return item.id;
    }

    trackVentasById(index: number, item: Ventas) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-historial-popup',
    template: ''
})
export class HistorialPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historialPopupService: HistorialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.historialPopupService
                    .open(HistorialDialogComponent as Component, params['id']);
            } else {
                this.historialPopupService
                    .open(HistorialDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
