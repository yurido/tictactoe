import { Injectable }   from '@angular/core';

import { HEROES }       from '../model/mock-heroes';
import { Hero }         from '../model/hero';

@Injectable()
export class HeroService {
    getHeroes() {
        return Promise.resolve(HEROES);
    }

    getHeroesSlowly() {
      return new Promise<Hero[]>(resolve =>
        setTimeout(() => resolve(HEROES), 2000) // 2 seconds
      );
    }

    getHero(id: number) {
      return this.getHeroes()
                 .then(heroes => heroes.find(hero => hero.id === id));
    }

}
