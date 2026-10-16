import { Section } from "./section.model";

export interface BookQuest {
    id: string;
    title: string;
    sections?: Section[];
}
