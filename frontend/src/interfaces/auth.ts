export type IResponseLogin = IResponseLoginSuccess & ErrorResponse;

export interface IResponseLoginSuccess {
	access_token: string;
	code: number;
	role: string;
	username: string;
}

export interface ErrorResponse {
	status: number;
	message: string;
	errors?: Record<string, string>;
}
