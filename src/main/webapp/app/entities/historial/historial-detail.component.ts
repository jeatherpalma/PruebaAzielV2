import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Historial } from './historial.model';
import { HistorialService } from './historial.service';

@Component({
    selector: 'jhi-historial-detail',
    templateUrl: './historial-detail.component.html'
})
export class HistorialDetailComponent implements OnInit, OnDestroy {

    historial: Historial;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private historialService: HistorialService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHistorials();
    }

    load(id) {
        this.historialService.find(id).subscribe((historial) => {
            this.historial = historial;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHistorials() {
        this.eventSubscriber = this.eventManager.subscribe(
            'historialListModification',
            (response) => this.load(this.historial.id)
        );
    }
}
