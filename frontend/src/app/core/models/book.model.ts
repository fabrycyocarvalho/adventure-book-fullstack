import { Section } from "./section.model";

export type DifficultyBook = 'EASY' | 'MEDIUM' | 'HARD';

export interface Book {
    id: string;
    title: string;
    author: string;
    description?: string;
    difficulty: DifficultyBook;
    genre?: string;
    durationMinutes?: string;
    sections?: Section[];
    tags?: string[];
    chaptersCount: number; //temp
}
