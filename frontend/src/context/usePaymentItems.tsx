import { IMenuOrderItem } from '@interfaces/index';
import React, { createContext } from 'react';

interface IPaymentItem {
	items: IMenuOrderItem[];
	total: number;
	tableName: string;
}

interface IUsePaymentItemsContext {
	paymentItem: IPaymentItem;
	setPaymentItem: (payment: IPaymentItem) => void;
}

const PaymentItemsContext = createContext<IUsePaymentItemsContext | null>(null);

const PaymentItemsProvider: React.FC<{ children: React.ReactNode }> = (props) => {
	const [paymentItem, setPaymentItem] = React.useState<IPaymentItem>({
		items: [],
		total: 0,
		tableName: '',
	});
	return (
		<PaymentItemsContext.Provider value={{ paymentItem, setPaymentItem }}>
			{props.children}
		</PaymentItemsContext.Provider>
	);
};

function usePaymentItems() {
	const context = React.useContext(PaymentItemsContext);
	if (typeof context === 'undefined' || context === null)
		throw new Error('usePaymentItems must be used within PaymentItemsProvider');
	return context;
}

export { usePaymentItems, PaymentItemsProvider };
