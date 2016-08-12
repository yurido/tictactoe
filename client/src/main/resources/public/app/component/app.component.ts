import { Component } from '@angular/core';
import '../rxjs-extensions';

@Component({
    selector: 'my-app',
    templateUrl: '../../html/app.component.html',
    styleUrls:  ['../../css/app.component.css']
})
export class AppComponent {
    title = 'Tour of Heroes';
}
