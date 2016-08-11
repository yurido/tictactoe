import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import { Hero }                 from '../model/hero';
import { HeroDetailComponent }  from './hero-detail.component';
import { HeroService }          from '../service/hero.service';

@Component({
    selector: 'my-heroes',
    templateUrl: '../../html/heroes.component.html',
    styleUrls:  ['../../css/heroes.component.css']
})

export class HeroesComponent implements OnInit {
    selectedHero: Hero;
    addingHero = false;
    heroes:       Hero[];
    error: any;

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

    addHero() {
      this.addingHero = true;
      this.selectedHero = null;
    }

    close(savedHero: Hero) {
      this.addingHero = false;
      if (savedHero) { this.getHeroes(); }
    }

    deleteHero(hero: Hero, event: any) {
      event.stopPropagation();
      this.heroService
          .delete(hero)
          .then(res => {
            this.heroes = this.heroes.filter(h => h !== hero);
            if (this.selectedHero === hero) { this.selectedHero = null; }
          })
          .catch(error => this.error = error);
    }


}
