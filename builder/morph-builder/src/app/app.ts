import { Component } from '@angular/core';
import { MorphBuilderComponent } from './components/morph-builder/morph-builder.component';

@Component({
  selector: 'app-root',
  imports: [MorphBuilderComponent],
  template: '<app-morph-builder></app-morph-builder>',
  styleUrl: './app.scss'
})
export class App {
}
