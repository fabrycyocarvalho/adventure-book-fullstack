import { Service, signal } from '@angular/core';

@Service()
export class BookStatsService {
    
    booksSize = signal(0);

}
