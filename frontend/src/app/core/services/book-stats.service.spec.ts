import { TestBed } from '@angular/core/testing';
import { BookStatsService } from './book-stats.service';

describe('BooksStatsService', () => {
  let service: BookStatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookStatsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
