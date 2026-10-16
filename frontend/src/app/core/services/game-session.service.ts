import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { tap } from 'rxjs';
import { GameSession } from '../models/game-session.model';
import { ConfigService } from './config.service';

const USERNAME_KEY = '__ukey';

@Service()
export class GameSessionService {
    private http = inject(HttpClient);
    private config = inject(ConfigService);
    private readonly API = `${this.config.apiUrl}/game-sessions`;

    get username() {
        return sessionStorage.getItem(USERNAME_KEY);
    }

    saveProgress(bookId: string, username: string, healthPoints: number, currentSectionId: number) {
        return this.http
            .post<any>(this.API, { bookId, currentSectionId, username, healthPoints })
            .pipe(tap(() => sessionStorage.setItem(USERNAME_KEY, username)));
    }

    getGameSession(bookId: string) {
        return this.http.get<GameSession>(`${this.API}/${this.username}/${bookId}`);
    }

    clearProgress() {
        return this.http
            .delete<void>(`${this.API}/${this.username}`);
    }
}
