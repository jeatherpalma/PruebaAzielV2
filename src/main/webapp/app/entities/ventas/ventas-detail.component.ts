import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ventas } from './ventas.model';
import { VentasService } from './ventas.service';

@Component({
    selector: 'jhi-ventas-detail',
    templateUrl: './ventas-detail.component.html'
})
export class VentasDetailComponent implements OnInit, OnDestroy {

    ventas: Ventas;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ventasService: VentasService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVentas();
    }

    load(id) {
        this.ventasService.find(id).subscribe((ventas) => {
            this.ventas = ventas;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVentas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ventasListModification',
            (response) => this.load(this.ventas.id)
        );
    }
}
