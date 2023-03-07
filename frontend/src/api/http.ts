import { getTokenService } from '@utils/localStorage';
import axios from 'axios';
import type { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { useNavigate } from 'react-router-dom';

class Http {
	uniqueInstance: AxiosInstance;
	constructor() {
		const baseURL: string = import.meta.env.VITE_SERVER;
		if (!baseURL) throw new Error('VITE_SERVER is not defined');
		this.uniqueInstance = axios.create({
			baseURL,
			timeout: 10000,
			headers: {
				'Content-Type': 'application/json',
			},
		});

		this.uniqueInstance.interceptors.request.use(this.onRequest, this.onRequestError);
		this.uniqueInstance.interceptors.response.use(this.onResponse, this.onResponseError);
	}
	private onRequest = (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
		console.info(`[request] [${JSON.stringify(config)}]`);
		const token = getTokenService();
		if (token) {
			config.headers['Authorization'] = 'Bearer ' + token;
		}
		// config.headers['Content-Type'] = 'application/json';
		return config;
	};

	private onRequestError = (error: AxiosError): Promise<AxiosError> => {
		console.error(`[request error] [${JSON.stringify(error)}]`);
		return Promise.reject(error);
	};

	private onResponse = (response: AxiosResponse): AxiosResponse => {
		console.info(`[response] [${JSON.stringify(response)}]`);
		return response;
	};

	private onResponseError = (error: AxiosError): Promise<AxiosError> => {
		console.error(`[response error] [${JSON.stringify(error)}]`);
		const originalRequest = error.config;
		const navigate = useNavigate();

		if (error.response?.status === 401 && originalRequest?.url?.includes('/auth/login') === false) {
			navigate('/logout');
			return Promise.reject(error);
		}
		return Promise.reject(error);
	};
}

const http = new Http().uniqueInstance;

export default http;
