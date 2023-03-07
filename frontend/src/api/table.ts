import type { AxiosInstance } from 'axios';
import http from './http';
import { ITableResponse } from '@interfaces/table';
import axios from 'axios';

class TableApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/tables`;
		this.request = axios.create({
			baseURL: `http://localhost:8081`,
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json',
			},
		});
	}

	public async getTables(status?: string): Promise<ITableResponse[]> {
		const config = status ? { params: { status } } : {};

		const response = await this.request.get<ITableResponse[]>(`${this.url}`, config);
		return response.data;
	}
}

const tableApi = new TableApi();

export default tableApi;
