const getTokenService = (): string | null => {
	const token = localStorage.getItem('token');
	if (!token) return null;
	return token;
};

const setTokenService = (token: string): void => {
	localStorage.setItem('token', token);
};

const removeTokenService = (): void => {
	localStorage.removeItem('token');
};

export { getTokenService, setTokenService, removeTokenService };
