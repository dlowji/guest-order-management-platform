export interface IMenuOrderItem {
	orderLineItemId?: number;
	dishId: string;
	title: string;
	price: number;
	quantity: number;
	note?: string;
	image: string;
}

export interface IMenuItem {
	orderLineItemId?: number;
	id: string;
	image: string;
	title: string;
	price: number;
	status: string;
	icon?: string;
}
