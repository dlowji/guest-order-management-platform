import { OrderLineItemStatusResponse } from '@constants/orderLineItemStatus';
import { IOrderDetails } from '@customTypes/index';
import { IMenuOrderItem } from '@interfaces/index';
import React, { createContext } from 'react';

interface IUseOrderDetailContext {
	orderDetail: IOrderDetails | null;
	orderLineItems: IMenuOrderItem[];
	setOrderDetail: (orderDetail: IOrderDetails) => void;
	handleAcceptDish: (dishId: string) => void;
	handleCancelDish: (dishId: string) => void;
	handleMarkDoneDish: (dishId: string) => void;
	totalAccept: number;
}

const OrderDetailContext = createContext<IUseOrderDetailContext | null>(null);

const OrderDetailProvider: React.FC<{ children: React.ReactNode }> = (props) => {
	const [orderDetail, setOrderDetail] = React.useState<IOrderDetails | null>(null);
	const [orderLineItems, setOrderLineItems] = React.useState<IMenuOrderItem[]>([]);
	const [totalAccept, setTotalAccept] = React.useState<number>(0);

	React.useEffect(() => {
		if (orderDetail?.orderId) {
			setOrderLineItems(orderDetail.orderLineItemResponseList || []);
		}
	}, [orderDetail?.orderId]);

	const handleAcceptDish = (dishId: string) => {
		const newOrderLineItems = orderLineItems.map((item) => {
			if (item.dishId === dishId) {
				return {
					...item,
					orderLineItemStatus: OrderLineItemStatusResponse.COOKING,
				};
			}
			return item;
		});
		if (totalAccept < orderLineItems.length) {
			setTotalAccept((prev) => prev + 1);
		}
		setOrderLineItems(newOrderLineItems);
	};

	const handleCancelDish = (dishId: string) => {
		const newOrderLineItems = orderLineItems.map((item) => {
			if (item.dishId === dishId) {
				return {
					...item,
					orderLineItemStatus: OrderLineItemStatusResponse.STOCK_OUT,
				};
			}
			return item;
		});
		if (totalAccept < orderLineItems.length) {
			setTotalAccept((prev) => prev + 1);
		}
		setOrderLineItems(newOrderLineItems);
	};

	const handleMarkDoneDish = (dishId: string) => {
		const newOrderLineItems = orderLineItems.map((item) => {
			if (item.dishId === dishId) {
				return {
					...item,
					orderLineItemStatus: OrderLineItemStatusResponse.COOKED,
				};
			}
			return item;
		});
		if (totalAccept < orderLineItems.length) {
			setTotalAccept((prev) => prev + 1);
		}
		setOrderLineItems(newOrderLineItems);
	};

	return (
		<OrderDetailContext.Provider
			value={{
				orderDetail,
				setOrderDetail,
				orderLineItems,
				handleAcceptDish,
				handleCancelDish,
				handleMarkDoneDish,
				totalAccept,
			}}
		>
			{props.children}
		</OrderDetailContext.Provider>
	);
};

function useOrderDetail() {
	const context = React.useContext(OrderDetailContext);
	if (typeof context === 'undefined' || context === null)
		throw new Error('useOrderDetail must be used within OrderDetailProvider');
	return context;
}

export { useOrderDetail, OrderDetailProvider };
