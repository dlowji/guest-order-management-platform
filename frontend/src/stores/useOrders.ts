// import { IMenuOrderItem } from '@interfaces/index';
// import { create } from 'zustand';
// import { persist } from 'zustand/middleware';

// interface IOrder {
// 	id: string;
// 	orderItems: IMenuOrderItem[];
// }

// interface OrdersState {
// 	orders: IOrder[];
// 	addToOrder: (orderId: string, dish: IMenuOrderItem) => void;
// 	setOrder: (order: IOrder) => void;
// 	increment: (orderId: string, dishId: string) => void;
// 	decrement: (orderId: string, dishId: string) => void;
// 	removeItem: (orderId: string, dishId: string) => void;
// 	removeAll: (orderId: string) => void;
// 	updateNote: (orderId: string, dishId: string, note: string) => void;
// }

// export const useOrders = create<OrdersState>()(
// 	persist(
// 		(set) => ({
// 			orders: [],
// 			addToOrder: (orderId: string, dish: IMenuOrderItem) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === orderId);
// 					if (currentOrderItem) {
// 						const currentDishItem = currentOrderItem.orderItems.find(
// 							(item) => item.dishId === dish.dishId,
// 						);
// 						if (currentDishItem) {
// 							currentDishItem.quantity += 1;
// 						} else {
// 							currentOrderItem.orderItems.push(dish);
// 						}
// 						return {
// 							orders: [...state.orders],
// 						};
// 					} else {
// 						return {
// 							orders: [...state.orders, { id: orderId, orderItems: [dish] }],
// 						};
// 					}
// 				});
// 			},
// 			setOrder: (order: IOrder) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === order.id);
// 					if (currentOrderItem) {
// 						currentOrderItem.orderItems = [...order.orderItems];
// 						return {
// 							orders: [...state.orders],
// 						};
// 					}
// 					return {
// 						orders: [...state.orders, order],
// 					};
// 				});
// 			},
// 			increment: (orderId: string, dishId: string) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === orderId);
// 					if (currentOrderItem) {
// 						const currentDishItem = currentOrderItem.orderItems.find(
// 							(item) => item.dishId === dishId,
// 						);
// 						if (currentDishItem) {
// 							currentDishItem.quantity += 1;
// 						}
// 					}
// 					return {
// 						orders: [...state.orders],
// 					};
// 				});
// 			},
// 			decrement: (orderId: string, dishId: string) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === orderId);
// 					if (currentOrderItem) {
// 						const currentDishItem = currentOrderItem.orderItems.find(
// 							(item) => item.dishId === dishId,
// 						);
// 						if (currentDishItem && currentDishItem.quantity > 1) {
// 							currentDishItem.quantity -= 1;
// 						}
// 					}
// 					return {
// 						orders: [...state.orders],
// 					};
// 				});
// 			},
// 			removeItem: (orderId: string, dishId: string) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === orderId);
// 					if (currentOrderItem) {
// 						const currentDishItem = currentOrderItem.orderItems.find(
// 							(item) => item.dishId === dishId,
// 						);
// 						if (currentDishItem) {
// 							currentOrderItem.orderItems = currentOrderItem.orderItems.filter(
// 								(item) => item.dishId !== dishId,
// 							);
// 						}
// 					}
// 					return {
// 						orders: [...state.orders],
// 					};
// 				});
// 			},
// 			removeAll: (orderId: string) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.filter((item) => item.id === orderId);
// 					if (currentOrderItem && currentOrderItem.length > 0) {
// 						state.orders = state.orders.filter((item) => item.id !== orderId);
// 					}
// 					return {
// 						orders: [...state.orders],
// 					};
// 				});
// 			},
// 			updateNote: (orderId: string, dishId: string, note: string) => {
// 				return set((state) => {
// 					const currentOrderItem = state.orders.find((item) => item.id === orderId);
// 					if (currentOrderItem) {
// 						const currentDishItem = currentOrderItem.orderItems.find(
// 							(item) => item.dishId === dishId,
// 						);
// 						if (currentDishItem) {
// 							currentDishItem.note = note;
// 						}
// 					}
// 					return {
// 						orders: [...state.orders],
// 					};
// 				});
// 			},
// 		}),
// 		{
// 			name: 'orders',
// 		},
// 	),
// );
