import { Routes, RouterModule } from '@angular/router';

import { HeroesComponent }      from './component/heroes.component';
import { DashboardComponent }   from './component/dashboard.component';
import { HeroDetailComponent }  from './component/hero-detail.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        component: DashboardComponent
    },
    {
        path: 'detail/:id',
        component: HeroDetailComponent
    },
    {
        path: 'heroes',
        component: HeroesComponent
    }
];

export const routing = RouterModule.forRoot(appRoutes);
