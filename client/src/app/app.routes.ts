import { provideRouter, RouterConfig }  from '@angular/router';

import { HeroesComponent }      from './component/heroes.component';
import { DashboardComponent }   from './component/dashboard.component';
import { HeroDetailComponent }  from './component/hero-detail.component';

const routes: RouterConfig = [
    {
        path: 'heroes',
        component: HeroesComponent
    },
    {
        path: 'dashboard',
        component: DashboardComponent
    },
    {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
    },
    {
        path: 'detail/:id',
        component: HeroDetailComponent
    }
];

export const appRouterProviders = [
    provideRouter(routes)
];
