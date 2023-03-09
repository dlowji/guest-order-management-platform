import type { AxiosInstance } from 'axios';
import http from './http';

class OrderApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/orders`;
		this.request = http;
	}

	public async placeTableOrder(tableId: string, userId: string): Promise<any> {
		const response = await this.request.post(`${this.url}`, { userId });
		return response.data;
	}
}

const orderApi = new OrderApi();

export default orderApi;
