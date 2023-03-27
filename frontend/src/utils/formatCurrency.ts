export function formatCurrency(value: number) {
	return new Intl.NumberFormat('vi-VN', {
		style: 'currency',
		currency: 'VND',
	}).format(value);
}

export function convertToUSD(value: number) {
	return Math.floor(value / 23000);
}
