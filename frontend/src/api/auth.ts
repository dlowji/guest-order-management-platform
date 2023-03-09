import type { AxiosInstance } from 'axios';
import http from './http';
import axios from 'axios';
import { TUser } from '@customTypes/index';
import { setTokenService } from '@utils/localStorage';

class AuthApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/accounts/auth`;
		this.request = axios.create({
			baseURL: `http://localhost:8081`,
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json',
			},
		});
	}

	public async login(username: string, password: string): Promise<string> {
		const response = await this.request.post<string>(`/login`, { username, password });
		if (response.data) {
			setTokenService(response.data);
		}
		return response.data;
	}

	public async getMe(token: string | null): Promise<TUser> {
		const response = await this.request.get<TUser>(`/me`, {
			headers: {
				Authorization: `Bearer ${token}`,
			},
		});
		return response.data;
	}
}

const authApi = new AuthApi();

export default authApi;
