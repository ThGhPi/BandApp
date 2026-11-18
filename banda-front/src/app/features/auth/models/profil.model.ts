export interface Profil {
    id: number;
    lastname: string;
    firstname: string;
    username: string;
    email: string;
    role: null |'member' | 'arr' | 'org' | 'admin';
    phoneNumber: string;
    birthday: string;
    address: string | null;
    groups: string[] | null;
    instruments: string[] | null;
}