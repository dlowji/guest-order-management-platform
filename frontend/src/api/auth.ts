import type { AxiosInstance } from 'axios';
import http from './http';
import { TUser } from '@customTypes/index';
import { IResponseLogin } from '@interfaces/auth';
import Role from '@constants/ERole';

class AuthApi {
	private url: string;
	private request: AxiosInstance;

	constructor() {
		this.url = `/accounts/auth`;
		this.request = http;
	}

	public async login(
		username: string,
		password: string,
	): Promise<{
		data: string | Record<string, string>;
		status: number;
		message: string;
	}> {
		try {
			const response = await this.request.post<IResponseLogin>(`${this.url}/login`, {
				username,
				password,
			});
			console.log('🚀 ~ AuthApi ~ login ~ response:', response);
			if (response.data.code === 0) {
				const { access_token } = response.data;
				return { data: access_token, status: response.status, message: 'Login successfully' };
			}

			return {
				status: response.status,
				message: response.data.message,
				data: response.data.errors as Record<string, string>,
			};
		} catch (error) {
			console.log('🚀 ~ AuthApi ~ login ~ error:', error);
			return Promise.reject(error);
		}
	}

	// public async getMe(token: string | null): Promise<TUser> {
	// 	const response = await this.request.get<TUser>(`/me`, {
	// 		headers: {
	// 			Authorization: `Bearer ${token}`,
	// 		},
	// 	});
	// 	return response.data;
	// }

	public async getMe(): Promise<Partial<TUser>> {
		const response = await this.request.get<{
			data: {
				accountId: string;
				username: string;
				employeeId: string;
				roleName: string;
			};
			code: number;
			message: string;
		}>(`/accounts/getme`);
		console.log('🚀 ~ AuthApi ~ getMe ~ response', response);

		return {
			...response.data.data,
			accountId: response.data.data.accountId,
			id: response.data.data.employeeId,
			roleName: response.data.data.roleName as Role,
		};
	}

	public async logout(): Promise<{
		status: number;
		message: string;
	}> {
		try {
			console.log(this.request);

			const response = await this.request.post<{
				code: number;
				message: string;
			}>(`${this.url}/logout`);
			if (response.data.code === 0) {
				return {
					status: response.status,
					message: response.data.message,
				};
			}
			return {
				status: 400,
				message: 'Logout failed! Please try again later.',
			};
		} catch (error) {
			console.log('🚀 ~ AuthApi ~ logout ~ error', error);
			return {
				status: 400,
				message: 'Logout failed! Please try again later.',
			};
		}
	}
}

const authApi = new AuthApi();

export default authApi;
