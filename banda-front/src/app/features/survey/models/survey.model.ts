import { Choice } from "./choice.model";

export interface Survey {
  id: number;
  question: string;
  scheduledEnd: string;
  multiplicity: boolean;
  totalVotes: number;
  closed: boolean;
  choices: Choice[];
}
