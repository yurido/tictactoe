import { Component }          from '@angular/core';
import { HeroService }        from './hero.service';
import { ROUTER_DIRECTIVES }  from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { HeroesComponent }    from './heroes.component';

@Component({
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <nav>
        <a [routerLink]="['/dashboard']" routerLinkActive="active">Dashboard</a>
        <a [routerLink]="['/heroes']" routerLinkActive="active">Heroes</a>
    </nav>
    <router-outlet></router-outlet>
  `,
  directives: [ROUTER_DIRECTIVES],
  providers:  [HeroService],
  precompile: [DashboardComponent, HeroesComponent]
})

export class AppComponent {
    title = 'Tour of Heroes';
}
