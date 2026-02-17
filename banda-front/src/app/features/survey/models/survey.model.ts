import { Choice } from "./choice.model";

export interface Survey {
  id: number;
  question: string;
  scheduledEnd: string;
  multiplicity: boolean;
  totalVote: number;
  options: Choice[];
}
