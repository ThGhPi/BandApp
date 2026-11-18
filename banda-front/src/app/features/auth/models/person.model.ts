export interface Person {
    id: number;
    lastname: string;
    firstname: string;
    username: string;
    email: string;
    phoneNumber: string;
    birthday: string;
    address: string | null;
    instruments: string[] | null;
}