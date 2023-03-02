import { IMenuOrderItem } from '@interfaces/index';
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface MenuItemsOrderState {
	menuItemsOrder: IMenuOrderItem[];
	setMenuItemsOrder: (menuItems: IMenuOrderItem) => void;
	increment: (id: string | number) => void;
	decrement: (id: string | number) => void;
	removeItem: (id: string | number) => void;
	updateNote: (id: string | number, note: string) => void;
	removeAll: () => void;
}

export const useMenuItemsOrder = create<MenuItemsOrderState>()(
	persist(
		(set) => ({
			menuItemsOrder: [],
			setMenuItemsOrder: (menuItemsOrder: IMenuOrderItem) => {
				return set((state) => {
					const currentOrderItem = state.menuItemsOrder.find(
						(item) => item.id === menuItemsOrder.id,
					);
					if (currentOrderItem) {
						currentOrderItem.quantity += 1;
						return {
							menuItemsOrder: [...state.menuItemsOrder],
						};
					}
					return {
						menuItemsOrder: [...state.menuItemsOrder, menuItemsOrder],
					};
				});
			},
			increment: (id: string | number) => {
				return set((state) => {
					const currentOrderItem = state.menuItemsOrder.find((item) => item.id === id);
					if (currentOrderItem) {
						currentOrderItem.quantity += 1;
						return {
							menuItemsOrder: [...state.menuItemsOrder],
						};
					}
					return {
						menuItemsOrder: [...state.menuItemsOrder],
					};
				});
			},
			decrement: (id: string | number) => {
				return set((state) => {
					const currentOrderItem = state.menuItemsOrder.find((item) => item.id === id);
					if (currentOrderItem && currentOrderItem.quantity > 1) {
						currentOrderItem.quantity -= 1;
						return {
							menuItemsOrder: [...state.menuItemsOrder],
						};
					}
					return {
						menuItemsOrder: [...state.menuItemsOrder],
					};
				});
			},
			removeItem: (id: string | number) => {
				return set((state) => {
					const currentOrderItem = state.menuItemsOrder.find((item) => item.id === id);
					if (currentOrderItem) {
						const newMenuItemsOrder = state.menuItemsOrder.filter((item) => item.id !== id);
						return {
							menuItemsOrder: [...newMenuItemsOrder],
						};
					}
					return {
						menuItemsOrder: [...state.menuItemsOrder],
					};
				});
			},
			updateNote: (id: string | number, note: string) => {
				return set((state) => {
					const currentOrderItem = state.menuItemsOrder.find((item) => item.id === id);
					if (currentOrderItem) {
						currentOrderItem.note = note;
					}
					return {
						menuItemsOrder: [...state.menuItemsOrder],
					};
				});
			},
			removeAll: () => {
				return set(() => {
					return {
						menuItemsOrder: [],
					};
				});
			},
		}),
		{
			name: 'menu-items-order',
		},
	),
);
