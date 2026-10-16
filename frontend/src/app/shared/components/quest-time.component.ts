import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-quest-time',
  styles: ``,
  template: `<img src="time.png" class="icon small">{{value()}} mim`,
  host: {
    'class': 'flex flex-row gap-1 items-center justify-center text-sm font-normal'
  }
})
export class QuestTimeComponent {
  public value = input.required();
}
