import { IMenuOrderItem } from '@interfaces/index';
import formatOrderItems from '@utils/formatOrderItems';
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface IMenuOrder {
	orderId: string;
	menuItemsOrder: IMenuOrderItem[];
	method: 'POST' | 'PUT';
}

interface MenuItemsOrderState {
	menuOrder: IMenuOrder;
	setMenuOrder: (menuOrder: IMenuOrder) => void;
	// setMenuItemsOrder: (menuItems: IMenuOrderItem) => void;
	addToOrder: (menuItems: IMenuOrderItem) => void;
	increment: (dishId: string, orderLineItemId?: number) => void;
	updateNote: (dishId: string, note: string, orderLineItemId?: number) => void;
	getMenuItemsOrder: () => IMenuOrderItem[];
}

export const useMenuItemsOrder = create(
	devtools<MenuItemsOrderState>(
		(set, get) => ({
			menuOrder: {
				orderId: '',
				menuItemsOrder: [],
				method: 'POST',
			},
			getMenuItemsOrder: () => {
				return [...get().menuOrder.menuItemsOrder];
			},
			setMenuOrder: (menuOrder: IMenuOrder) => {
				return set((state) => {
					return {
						menuOrder: {
							...state.menuOrder,
							...menuOrder,
						},
					};
				});
			},

			// setMenuItemsOrder: (menuItemsOrder: IMenuOrderItem) => {
			// 	return set((state) => {
			// 		const currentOrderItem = state.menuOrder.menuItemsOrder.find(
			// 			(item) => item.dishId === menuItemsOrder.dishId,
			// 		);
			// 		if (currentOrderItem) {
			// 			currentOrderItem.quantity += menuItemsOrder.quantity;
			// 			return {
			// 				menuOrder: {
			// 					...state.menuOrder,
			// 					menuItemsOrder: [...state.menuOrder.menuItemsOrder],
			// 				},
			// 			};
			// 		}
			// 		return {
			// 			menuOrder: {
			// 				...state.menuOrder,
			// 				menuItemsOrder: [...state.menuOrder.menuItemsOrder, menuItemsOrder],
			// 			},
			// 		};
			// 	});
			// },
			addToOrder: (menuItems: IMenuOrderItem) => {
				return set((state) => {
					const currentOrders = [...state.menuOrder.menuItemsOrder, menuItems];
					const formatedOrders = formatOrderItems([...currentOrders]);

					return {
						menuOrder: {
							...state.menuOrder,
							menuItemsOrder: formatedOrders,
						},
					};
				});
			},
			increment: (dishId: string, orderLineItemId?: number) => {
				return set((state) => {
					const currentOrderItem = state.menuOrder.menuItemsOrder.find(
						(item) => item.orderLineItemId === orderLineItemId && item.dishId === dishId,
					);
					if (currentOrderItem) {
						currentOrderItem.quantity += 1;
						return {
							menuOrder: {
								...state.menuOrder,
								menuItemsOrder: [...state.menuOrder.menuItemsOrder],
							},
						};
					}
					return {
						menuOrder: {
							...state.menuOrder,
							menuItemsOrder: [...state.menuOrder.menuItemsOrder],
						},
					};
				});
			},
			updateNote: (dishId: string, note: string, orderLineItemId?: number) => {
				return set((state) => {
					const currentOrderItem = state.menuOrder.menuItemsOrder.find(
						(item) => item.dishId === dishId && item.orderLineItemId === orderLineItemId,
					);
					if (currentOrderItem) {
						currentOrderItem.note = note;
					}
					return {
						menuOrder: {
							...state.menuOrder,
							menuItemsOrder: [...state.menuOrder.menuItemsOrder],
						},
					};
				});
			},
		}),
		{
			name: 'Menu Items Order',
		},
	),
);
