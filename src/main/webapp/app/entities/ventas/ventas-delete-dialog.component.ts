import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ventas } from './ventas.model';
import { VentasPopupService } from './ventas-popup.service';
import { VentasService } from './ventas.service';

@Component({
    selector: 'jhi-ventas-delete-dialog',
    templateUrl: './ventas-delete-dialog.component.html'
})
export class VentasDeleteDialogComponent {

    ventas: Ventas;

    constructor(
        private ventasService: VentasService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ventasService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ventasListModification',
                content: 'Deleted an ventas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ventas-delete-popup',
    template: ''
})
export class VentasDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ventasPopupService: VentasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ventasPopupService
                .open(VentasDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
