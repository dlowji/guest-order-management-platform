import type { AxiosInstance } from 'axios';
import http from './http';
import { IOrderDetails, TOrder } from '@customTypes/index';
import { IMenuOrderItem } from '@interfaces/index';

class OrderApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/orders`;
		this.request = http;
	}

	public async placeTableOrder(
		tableId: string,
		accountId: string,
	): Promise<{
		code: number;
		orderId?: string;
		message?: string;
	}> {
		try {
			const response = await this.request.post<{
				orderId: string;
				code: number;
				message: string;
			}>(`${this.url}`, { accountId, tableId });

			if (response.data.code === 0) {
				return {
					code: 200,
					orderId: response.data.orderId,
				};
			}
			return {
				code: 400,
				message: "Can't place order",
			};
		} catch (error) {
			return {
				code: 400,
				message: "Can't place order",
			};
		}
	}

	public async getAll() {
		try {
			const response = await this.request.get<{
				code: number;
				message: string;
				data: TOrder[];
			}>(`${this.url}`);
			if (response.data.code === 0) {
				return {
					code: 200,
					data: response.data.data,
				};
			} else {
				return {
					code: 400,
					message: "Can't get all orders",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get all orders",
			};
		}
	}

	public async getById(id: string | undefined) {
		if (!id)
			return {
				code: 400,
				message: "Can't get order",
			};
		try {
			const response = await this.request.get<{
				code: number;
				message: string;
				data: IOrderDetails;
			}>(`${this.url}/${id}`);
			if (response.data.code === 0) {
				const { ...order } = response.data.data;

				return {
					code: 200,
					data: order,
				};
			} else {
				return {
					code: 400,
					message: "Can't get order",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get order",
			};
		}
	}

	public async updateOrderLineItems(
		orderId: string | undefined,
		items: IMenuOrderItem[] | undefined,
	) {
		if (!orderId || !items || items.length === 0) {
			return {
				code: 400,
				message: "Can't create order line items",
			};
		}
		try {
			const response = await this.request.post<{
				code: number;
				message: string;
				orderId: string;
			}>(`${this.url}/placed`, {
				orderId,
				orderLineItemRequestList: [...items],
			});
			if (response.data.code === 0) {
				return {
					code: 200,
					message: 'Order placed successfully',
				};
			} else {
				return {
					code: 400,
					message: "Can't create order line items",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't create order line items",
			};
		}
	}
}

const orderApi = new OrderApi();

export default orderApi;
