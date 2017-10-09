import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ventas } from './ventas.model';
import { VentasPopupService } from './ventas-popup.service';
import { VentasService } from './ventas.service';

@Component({
    selector: 'jhi-ventas-dialog',
    templateUrl: './ventas-dialog.component.html'
})
export class VentasDialogComponent implements OnInit {

    ventas: Ventas;
    isSaving: boolean;
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ventasService: VentasService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ventas.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ventasService.update(this.ventas));
        } else {
            this.subscribeToSaveResponse(
                this.ventasService.create(this.ventas));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ventas>) {
        result.subscribe((res: Ventas) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ventas) {
        this.eventManager.broadcast({ name: 'ventasListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-ventas-popup',
    template: ''
})
export class VentasPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ventasPopupService: VentasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ventasPopupService
                    .open(VentasDialogComponent as Component, params['id']);
            } else {
                this.ventasPopupService
                    .open(VentasDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
