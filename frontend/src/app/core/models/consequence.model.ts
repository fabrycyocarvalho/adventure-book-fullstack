
export type ConsequenceType = 'LOSE_HEALTH' | 'GAIN_HEALTH';

export interface Consequence {
    type: ConsequenceType;
    value: string;
    text: string;
}
