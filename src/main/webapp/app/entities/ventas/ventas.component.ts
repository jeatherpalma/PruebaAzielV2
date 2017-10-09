import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Ventas } from './ventas.model';
import { VentasService } from './ventas.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ventas',
    templateUrl: './ventas.component.html'
})
export class VentasComponent implements OnInit, OnDestroy {
ventas: Ventas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ventasService: VentasService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ventasService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ventas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVentas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ventas) {
        return item.id;
    }
    registerChangeInVentas() {
        this.eventSubscriber = this.eventManager.subscribe('ventasListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
