export interface IMenuOrderItem {
	id: string | number;
	name: string;
	price: number;
	quantity: number;
	note?: string;
	image: string;
}

export interface IMenuItem {
	id: string | number;
	image: string;
	title: string;
	price: number;
	status: string;
	icon?: string;
}
