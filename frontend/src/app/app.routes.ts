import { Routes } from '@angular/router';
import { LibraryComponent } from './features/library/library.component';
import { ManageBooksComponent } from './features/manage-books/manage-books.component';
import { QuestComponent } from './features/quest/quest.component';
import { DefaultLayoutComponent } from './layout/default-layout.component';
import { LandingLayoutComponent } from './layout/landing-layout.component';

export const routes: Routes = [
    {
        path: 'library',
        component: LandingLayoutComponent,
        children: [
            {
                path: '',
                component: LibraryComponent
            },
        ]
    },
    {
        path: 'quest',
        component: DefaultLayoutComponent,
        children: [
            {
                path: ':bookId',
                component: QuestComponent
            },
        ]
    },
    {
        path: 'manage-books',
        component: DefaultLayoutComponent,
        children: [
            {
                path: '',
                component: ManageBooksComponent
            },
        ]
    },
    { path: '**', redirectTo: '/library' }
];
