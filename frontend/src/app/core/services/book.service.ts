import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { tap } from 'rxjs';
import { BookQuest } from '../models/book-quest.model';
import { Book } from '../models/book.model';
import { BookStatsService } from './book-stats.service';
import { ConfigService } from './config.service';

@Service()
export class BookService {
    private http = inject(HttpClient);
    private config = inject(ConfigService);
    private bookStatsService = inject(BookStatsService);
    private readonly API = `${this.config.apiUrl}/books`;

    search(search: string, difficulty: string[], genres: string[]) {
        return this.http
            .get<Book[]>(`${this.API}?search=${search}&difficulty=${difficulty}&genres=${genres}`)
            .pipe(tap(books => this.bookStatsService.booksSize.set(books.length)));
    }

    getBooks() {
        return this.http
            .get<Book[]>(this.API)
            .pipe(tap(books => this.bookStatsService.booksSize.set(books.length)));
    }

    getGenres() {
        return this.http.get<string[]>(`${this.API}/genres`);
    }

    get(bookId: string) {
        return this.http.get<BookQuest>(`${this.API}/${bookId}`);
    }

    delete(bookId: string) {
        return this.http.delete(`${this.API}/${bookId}`);
    }

    importBookFile(file: File) {
        const formData = new FormData();
        formData.append('file', file, file.name);
        return this.http.post<void>(`${this.API}/import`, formData);
    }
}
