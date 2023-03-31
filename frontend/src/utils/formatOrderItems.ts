import { IMenuOrderItem } from '@interfaces/index';

function formatOrderItems(orderItems: IMenuOrderItem[]) {
	const orders = [...orderItems];
	console.log(orders);
	const formatedOrders = orders.reduce((acc, item) => {
		const currentOrderItem = acc.find(
			(order) => order.dishId === item.dishId && order.orderLineItemId === item.orderLineItemId,
		);

		if (currentOrderItem) {
			currentOrderItem.quantity += item.quantity;
		} else {
			acc.push(item);
		}

		return acc;
	}, [] as IMenuOrderItem[]);

	console.log(formatedOrders);
	return formatedOrders;
}

export default formatOrderItems;
