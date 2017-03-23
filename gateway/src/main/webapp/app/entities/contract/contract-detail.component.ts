import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Contract } from './contract.model';
import { ContractService } from './contract.service';

@Component({
    selector: 'jhi-contract-detail',
    templateUrl: './contract-detail.component.html'
})
export class ContractDetailComponent implements OnInit, OnDestroy {

    contract: Contract;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contractService: ContractService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['contract']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.contractService.find(id).subscribe(contract => {
            this.contract = contract;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
