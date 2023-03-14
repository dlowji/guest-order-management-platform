import * as React from 'react';
import OrderMain from '../modules/order/OrderMain';
import { Outlet } from 'react-router-dom';

interface IOrderPageProps {}

const OrderPage: React.FunctionComponent<IOrderPageProps> = () => {
	return (
		<div className="order">
			<OrderMain></OrderMain>
			<Outlet />
		</div>
	);
};

export default OrderPage;
