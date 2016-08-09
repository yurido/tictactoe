import { provideRouter, RouterConfig }  from '@angular/router';
import { HeroesComponent }              from './heroes.component';
import { DashboardComponent }           from './dashboard.component';

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
    }
];

export const appRouterProviders = [
    provideRouter(routes)
];
