import { Component }          from '@angular/core';
import { ROUTER_DIRECTIVES }  from '@angular/router';

import { HeroService }        from './hero.service';
import { DashboardComponent } from './dashboard.component';
import { HeroesComponent }    from './heroes.component';
import { HeroDetailComponent }from './hero-detail.component':

@Component({
    selector: 'my-app',
    templateUrl: '../html/app.component.html',
    styleUrls:  ['../css/app.component.css'],
    directives: [ROUTER_DIRECTIVES],
    providers:  [HeroService],
    precompile: [DashboardComponent, HeroesComponent, HeroDetailComponent]
})

export class AppComponent {
    title = 'Tour of Heroes';
}
