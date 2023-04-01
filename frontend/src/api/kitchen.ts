import type { AxiosInstance } from 'axios';
import http from './http';
import { ICategoryItem, TDish } from '@customTypes/index';
import { IMenuOrderItem } from '@interfaces/index';
import { OrderLineItemStatusResponse } from '@constants/orderLineItemStatus';

class KitchenApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/kitchens`;
		this.request = http;
	}

	public async getDishes(category?: string): Promise<TDish[]> {
		try {
			const response = await this.request.get<{
				code: number;
				data: TDish[];
			}>(category ? `${this.url}/menu?q=${category}` : `${this.url}/menu`);

			if (response.data.code === 0) {
				return response.data.data;
			}
			return [];
		} catch (error) {
			console.log(error);

			return [];
		}
	}

	public async getDishesByStatus(status = 'available'): Promise<TDish[]> {
		try {
			const response = await this.request.get<{
				code: number;
				data: TDish[];
			}>(`${this.url}/menu/status?q=${status}`);

			if (response.data.code === 0) {
				return response.data.data;
			}
			return [];
		} catch (error) {
			console.log(error);

			return [];
		}
	}

	public async getCategories(): Promise<ICategoryItem[]> {
		try {
			const response = await this.request.get<{
				code: number;
				data: ICategoryItem[];
			}>(`${this.url}/category`);

			if (response.data.code === 0) {
				return response.data.data;
			}
			return [];
		} catch (error) {
			console.log(error);
			return [];
		}
	}

	public async toggleDishStatus(id: string): Promise<{
		code: number;
		message: string;
	}> {
		try {
			const response = await this.request.post<{
				code: number;
				message: string;
			}>(`${this.url}/toggle/${id}`);

			if (response.data.code === 0) {
				return {
					code: 200,
					message: response.data.message,
				};
			}
			return {
				code: 500,
				message: response.data.message,
			};
		} catch (error) {
			console.log(error);
			return {
				code: 500,
				message: 'Something went wrong',
			};
		}
	}

	public async markDoneOrder(orderId: string, items: IMenuOrderItem[]) {
		if (!orderId) {
			return {
				code: 400,
				message: "Can't mark done order",
			};
		}
		try {
			const response = await this.request.post<{
				code: number;
				message?: string;
			}>(`${this.url}/mark-done`, {
				orderId,
				markDoneOrderLineItemRequests: items
					.filter((item) => {
						return item.orderLineItemStatus === OrderLineItemStatusResponse.COOKED;
					})
					.map((item) => {
						return {
							id: item.orderLineItemId,
						};
					}),
			});

			if (response.data.code === 0) {
				return {
					code: 200,
					message: 'Order marked done successfully',
				};
			} else {
				return {
					code: 400,
					message: response.data.message || "Can't mark done order",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't mark done order",
			};
		}
	}
}

const kitchenApi = new KitchenApi();

export default kitchenApi;
