export interface Person {
    id: number;
    firstname: string;
    lastname: string;
    username: string;
    email: string;
    phoneNumber: string;
    birthdate: string;
    address: string | null;
    instruments: string[] | null;
}