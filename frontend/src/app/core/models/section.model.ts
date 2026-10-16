import { Option } from "./option.model";

export type SectionType = 'NODE' | 'END' | 'BEGIN';

export interface Section {
    id: number;
    text: string;
    type: SectionType;
    options?: Option[];
}
