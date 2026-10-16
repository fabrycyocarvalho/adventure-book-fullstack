import { Consequence } from "./consequence.model";

export interface Option {
    description: string;
    tip?: string;
    gotoId: number;
    consequence?: Consequence;
}
