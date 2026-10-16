import { Dialog } from '@angular/cdk/dialog';
import { Component, inject, OnInit, signal } from '@angular/core';
import { Book, BookService } from '../../core';
import { ImportBookDialogComponent } from './import-book-dialog.component';

@Component({
  imports: [],
  selector: 'app-manage-books',
  template: `
  <header class="h-18 w-full sticky flex justify-center">
    <div class="flex flex-1 flex-row items-center justify-between">
      <button type="button" (click)="backToLibrary($event)" class="hover:text-(--app-text-secondary-hover) text-(--app-text) flex flex-row text-sm gap-2 items-center justify-center cursor-pointer transition-all duration-200" ><img src="arrow-back.png" class="icon small">Back To Library</button>
      <p class="text-(--app-text) text-2xl">Manage Books</p>
      <button (click)="openImportDialog()" id="sp-button" type="button" 
        class="flex flex-row gap-2 text-sm px-4 py-1 hover:bg-(--app-bg-hover) text-(--app-text) items-center justify-center cursor-pointer border rounded-lg">
      <img src="save.png" class="icon small">Import Book</button>            
    </div>
  </header>
  <main class="relative flex-1 overflow-y-auto">
    <div class="border rounded-lg border-(--app-accent) py-4 px-10 text-(--app-text) flex flex-col gap-4">
      <table class="table-auto text-sm">
        <thead>
          <tr>
            <th class="text-left">Title</th>
            <th class="text-left">Author</th>
            <th class="text-left">Description</th>
            <th class="text-left">Difficulty</th>
            <th class="text-left">Genre</th>
            <th class="text-left">Duration</th>
            <th class="text-left w-20"></th>
          </tr>
        </thead>
        <tbody>
          @for (book of books(); track $index) {
          <tr>
            <td>{{book.title}}</td>
            <td>{{book.author}}</td>
            <td>{{book.description}}</td>
            <td>{{book.difficulty}}</td>
            <td>{{book.genre}}</td>
            <td>{{book.durationMinutes}}</td>
            <td class="text-right">
              <button (click)="onDelete(book)" class="px-2 py-1 rounded-md bg-stone-700/50 text-amber-100 text-sm font-medium border border-stone-600/60 hover:bg-stone-700 hover:border-stone-500 transition-all duration-200 cursor-pointer">
                Delete
              </button>
            </td>
          </tr>
          }
        </tbody>
      </table>    
    </div>
  </main> `,
})
export class ManageBooksComponent implements OnInit {

  private bookService = inject(BookService);
  private dialog = inject(Dialog);

  protected books = signal<Book[]>([]);

  ngOnInit(): void {
    this.loadBooks();
  }

  private loadBooks() {
    this.bookService.getBooks().subscribe(books => this.books.set(books));
  }

  protected backToLibrary(event: Event) {
    event.stopPropagation();
    history.back();
  }

  onDelete(book: Book) {
    if (confirm('Are you sure you want to delete the book?')) {
      this.bookService.delete(book.id).subscribe(() => {
        this.books.update(current => current.filter(b => b.id !== book.id));
        alert('The book has been deleted successfully');
      });
    }
  }

  openImportDialog() {
    const dialogRef = this.dialog.open(ImportBookDialogComponent, {
      width: '450px',
    });

    dialogRef.closed.subscribe(result => {
      if (result instanceof File) {
        this.bookService.importBookFile(result).subscribe({
          next: () => {
            alert('The book has been imported successfully!');
            this.loadBooks();
          },
          error: (err) => {
            console.error('Erro ao importar o livro:', err);
            alert(err?.error['detail']);
          }
        });
      }
    });
  }
}
