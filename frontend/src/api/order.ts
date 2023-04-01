import type { AxiosInstance } from 'axios';
import http from './http';
import { BestSeller, IOrderDetails, TDashboardResponse, TOrder } from '@customTypes/index';
import { IMenuOrderItem } from '@interfaces/index';
import { statisticItemsDashboard } from '@constants/statisticItemsDashboard';
import { formatCurrency } from '@utils/formatCurrency';

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

	public async getAll(status?: string) {
		try {
			const response = await this.request.get<{
				code: number;
				message: string;
				data: TOrder[];
			}>(status ? `${this.url}?status=${status}` : `${this.url}`);
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

	public async getByTableId(tableId: string) {
		try {
			const response = await this.request.get<{
				code: number;
				message: string;
				data: TOrder;
			}>(`${this.url}?tableId=${tableId}`);
			if (response.data.code === 0) {
				return {
					code: 200,
					data: response.data.data,
				};
			} else {
				return {
					code: 400,
					message: "Can't get order by table id",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get order by table id",
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
				orderId: string;
				message?: string;
			}>(`${this.url}/placed`, {
				orderId,
				updateOrderLineItemRequests: [...items],
			});

			if (response.data.code === 0) {
				return {
					code: 200,
					message: 'Order placed successfully',
				};
			} else {
				return {
					code: 400,
					message: response.data.message || "Can't create order line items",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't create order line items",
			};
		}
	}

	public async progressOrder(orderId: string, items: IMenuOrderItem[]) {
		if (!orderId) {
			return {
				code: 400,
				message: "Can't progress order",
			};
		}
		try {
			const response = await this.request.post<{
				code: number;
				message?: string;
			}>(`${this.url}/progress`, {
				orderId,
				progressOrderLineItemRequestList: items.map((item) => ({
					id: item.orderLineItemId,
					quantity: item.quantity,
					orderLineItemStatus: item.orderLineItemStatus,
				})),
			});

			if (response.data.code === 0) {
				return {
					code: 200,
					message: 'Order progressed successfully',
				};
			} else {
				return {
					code: 400,
					message: response.data.message || "Can't progress order",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't progress order",
			};
		}
	}

	public async getDashboardStatistics() {
		try {
			const response = await this.request.get<TDashboardResponse>(`${this.url}/home`);
			if (response.data.code === 0) {
				const statistics = response.data as TDashboardResponse;
				const newStatisticItems = statisticItemsDashboard.map((item) => {
					const value = statistics[item.id as keyof TDashboardResponse];
					if (item.id === 'revenue') {
						return {
							...item,
							value: formatCurrency(value as number),
						};
					}
					return {
						...item,
						value: value.toString(),
					};
				});
				return {
					code: 200,
					data: newStatisticItems || [],
				};
			} else {
				return {
					code: 400,
					message: "Can't get home",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get home",
			};
		}
	}

	public async checkoutOrder(orderId: string) {
		if (!orderId) {
			return {
				code: 400,
				message: "Can't checkout order",
			};
		}
		try {
			const response = await this.request.post<{
				code: number;
				message?: string;
			}>(`${this.url}/checkout`, {
				orderId,
			});

			if (response.data.code === 0) {
				return {
					code: 200,
					message: 'Order checked out successfully',
				};
			} else {
				return {
					code: 400,
					message: response.data.message || "Can't checkout order",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't checkout order",
			};
		}
	}

	public async getBestSeller(limit = 5) {
		try {
			const response = await this.request.get<{
				code: number;
				message: string;
				data: BestSeller[];
			}>(`${this.url}/best-seller/${limit}`);
			if (response.data.code === 0) {
				const bestSeller = response?.data?.data || [];
				console.log('🚀 ~ OrderApi ~ getBestSeller ~ bestSeller:', bestSeller);
				return {
					code: 200,
					items: bestSeller,
				};
			} else {
				return {
					code: 400,
					message: "Can't get best seller",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get best seller",
			};
		}
	}

	public async getHistory(
		timestamp: number,
		filter: 'day' | 'month' | 'year',
	): Promise<{
		code: number;
		message?: string;
		data?: IOrderDetails[];
	}> {
		try {
			const response = await this.request.post<{
				code: number;
				message: string;
				data: IOrderDetails[];
			}>(`${this.url}/filter`, {
				timestamp,
				filter,
			});
			if (response.data.code === 0) {
				const history = response?.data?.data || [];
				return {
					code: 200,
					data: history,
				};
			} else {
				return {
					code: 400,
					message: "Can't get history",
				};
			}
		} catch (error) {
			return {
				code: 400,
				message: "Can't get history",
			};
		}
	}
}

const orderApi = new OrderApi();

export default orderApi;
