export interface RegisterRequest {
    id: number;
    firstname: string;
    lastname: string;
    username: string;
    email: string;
    trialPassword: string;
    phoneNumber: string;
    birthdate: string;
    address: string | null;
    instruments: string[] | null;
}