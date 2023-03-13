import { TDish } from '@customTypes/index';
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface IMenuItemsState {
	menuItems: TDish[];
	setMenuItems: (menuItems: TDish) => void;
}
export const useMenuItems = create<IMenuItemsState>()(
	devtools(
		(set) => ({
			menuItems: [],
			setMenuItems: (menuItems: TDish) =>
				set((state) => {
					return { menuItems: [...state.menuItems, menuItems] };
				}),
		}),
		{
			name: 'Menu Items',
		},
	),
);
