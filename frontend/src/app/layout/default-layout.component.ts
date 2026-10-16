import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  imports: [RouterOutlet],
  selector: 'app-default-layout',
  styles: ``,
  template: `
     <div class="flex h-screen overflow-hidden font-serif max-w-280 mx-auto sm:px-6 lg:px-10 lg:py-8">
      <div class="flex-1 flex flex-col min-w-0 overflow-hidden">
        <router-outlet></router-outlet>
      </div>
     </div>
  `,
})
export class DefaultLayoutComponent { }
