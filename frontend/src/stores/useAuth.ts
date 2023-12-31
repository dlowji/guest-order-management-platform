import { TUser } from '@customTypes/index';
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface IUserState {
	user: Partial<TUser> | null;
	setUser: (user: TUser) => void;
	removeUser: () => void;
}

export const useAuth = create<IUserState>()(
	devtools(
		(set) => ({
			user: null,
			setUser: (user: TUser) => set({ user }),
			removeUser: () => set({ user: null }),
		}),
		{
			name: 'Auth store',
		},
	),
);
