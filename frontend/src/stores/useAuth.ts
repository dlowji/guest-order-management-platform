import { TUser } from '@customTypes/index';
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface IUserState {
	user: TUser | null;
	setUser: (user: TUser) => void;
}

export const useAuth = create<IUserState>()(
	devtools(
		(set) => ({
			user: null,
			setUser: (user: TUser) => set({ user }),
		}),
		{
			name: 'Auth store',
		},
	),
);
