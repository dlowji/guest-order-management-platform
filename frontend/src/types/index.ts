import Role from '@constants/ERole';

export type TStatusTable = 'FREE' | 'OCCUPIED' | 'CHECK_IN';

export type TUser = {
	id: string;
	username: string;
	password: string;
	fullName: string;
	email: string;
	gender: number;
	salary: number;
	dob: string;
	address: string;
	roleId?: string;
	role?: Role;
	phone: string;
};
