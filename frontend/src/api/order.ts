import type { AxiosInstance } from 'axios';
import http from './http';
import axios from 'axios';

class OrderApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/orders`;
		this.request = axios.create({
			baseURL: `http://localhost:8081`,
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json',
			},
		});
	}

	public async placeTableOrder(tableId: string, userId: string): Promise<any> {
		const response = await this.request.post(`/orders`, { userId });
		return response.data;
	}
}

const orderApi = new OrderApi();

export default orderApi;
