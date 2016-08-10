import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import { Hero }                 from '../model/hero';
import { HeroDetailComponent }  from './hero-detail.component';
import { HeroService }          from '../service/hero.service';

@Component({
    selector: 'my-heroes',
    templateUrl: '../../html/heroes.component.html',
    styleUrls:   ['../../css/heroes.component.css']
})

export class HeroesComponent implements OnInit {
    selectedHero:   Hero;
    heroes:         Hero[];

    ngOnInit() {
        this.getHeroes();
    }

    constructor(
        private router: Router,
        private heroService: HeroService) {
    }

    onSelect(hero: Hero) { this.selectedHero = hero; }

    getHeroes() {
        this.heroService.getHeroes().then(heroes => this.heroes = heroes);
    }

    gotoDetail() {
        this.router.navigate(['/detail', this.selectedHero.id]);
    }
}
