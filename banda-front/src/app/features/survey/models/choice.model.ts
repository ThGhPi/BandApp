export interface Choice {
  id: number;
  title: string;
  complement: string;
  url: string;
  votes: number;
  chosen: boolean;
}