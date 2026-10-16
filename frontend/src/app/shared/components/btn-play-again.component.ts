import { Component, output } from '@angular/core';

@Component({
  imports: [],
  selector: `btn-play-again`,
  template: `Play Again`,
  host: {
    'role': 'button',
    'type': 'button',
    'tabindex': '0',
    'class': 'text-center w-38 text-sm px-4 py-1 hover:bg-(--app-bg-hover) text-(--app-text) items-center justify-center cursor-pointer border rounded-lg',
    '(click)': 'onClick()'
  }
})
export class BtnPlayAgainComponent {
  readonly clicked = output<void>();

  protected onClick() {
    this.clicked.emit();
  }
}
