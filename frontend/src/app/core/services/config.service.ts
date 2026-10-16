import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Service()
export class ConfigService {
    private http = inject(HttpClient);
    private config: any;

    loadConfig() {
        return firstValueFrom(this.http.get('/config.json'))
            .then(data => {
                this.config = data;
            });
    }

    get apiUrl(): string {
        return this.config?.['apiUrl'] ?? '';
    }
}
