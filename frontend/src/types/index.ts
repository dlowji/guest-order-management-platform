import Role from '@constants/ERole';
import { IDashboard, IMenuOrderItem } from '@interfaces/index';

export type TStatusTable = 'FREE' | 'OCCUPIED' | 'CHECK_IN';
export type TStatusOrder = 'CREATED' | 'IN_PROCESSING' | 'CANCELED' | 'COMPLETED';

export type TUser = {
	id: string;
	username: string;
	password: string;
	fullName: string;
	email: string;
	// gender: number;
	// salary: number;
	// dob: string;
	// address: string;
	// phone: string;
	roleId?: string;
	roleName?: Role;
	accountId?: string;
	scheduleId?: string;
};

export type TDish = {
	// orderLineItemId: number;
	dishId: string;
	title: string;
	image: string;
	price: number;
	summary: string;
	dishStatus: string;
	categoryName: string;
	createdAt: string;
	updatedAt: string;
};

export type TOrder = {
	orderId: string;
	accountId: string;
	accountName?: string;
	tableName: string;
	orderStatus: TStatusOrder;
	grandTotal: number;
	createdAt: string;
	updatedAt: string;
};

export interface IOrderDetails extends TOrder {
	orderLineItemResponseList: IMenuOrderItem[];
	subTotal: number;
	itemDiscount: number;
	tax: number;
	promoCode: number;
	discount: number;
	lastProcessing?: string;
}

export interface ICategoryItem {
	categoryId: string;
	categoryName: string;
	icon: string;
	link: string;
}

export type TDashboardResponse = {
	code: number;
	message: string;
} & keyof IDashboard;

export type PaymentMethod = 'CASH' | 'CREDIT_CARD' | 'PAYPAL';

export type BestSeller = TDish & {
	totalOrdered: number;
};
