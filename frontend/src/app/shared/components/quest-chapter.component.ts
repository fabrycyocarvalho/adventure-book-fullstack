import { Component, input } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-quest-chapter',
  styles: ``,
  template: `<img src="section-book.png" class="icon small">{{value() || 0}} chapters`,
  host: {
    'class': 'flex flex-row gap-1 items-center justify-center text-sm font-normal'
  }
})
export class QuestChapterComponent {
  public value = input.required();
}
