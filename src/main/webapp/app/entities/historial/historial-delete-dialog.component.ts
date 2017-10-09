import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Historial } from './historial.model';
import { HistorialPopupService } from './historial-popup.service';
import { HistorialService } from './historial.service';

@Component({
    selector: 'jhi-historial-delete-dialog',
    templateUrl: './historial-delete-dialog.component.html'
})
export class HistorialDeleteDialogComponent {

    historial: Historial;

    constructor(
        private historialService: HistorialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historialService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'historialListModification',
                content: 'Deleted an historial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historial-delete-popup',
    template: ''
})
export class HistorialDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historialPopupService: HistorialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.historialPopupService
                .open(HistorialDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
