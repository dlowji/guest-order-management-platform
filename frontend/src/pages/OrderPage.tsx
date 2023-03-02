import * as React from 'react';
import OrderMain from '../modules/order/OrderMain';
import { Outlet, useParams } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';

interface IOrderPageProps {}

const OrderPage: React.FunctionComponent<IOrderPageProps> = () => {
	const { id } = useParams<{ id: string }>();
	const orderItems = useMenuItemsOrder((state) => state.menuItemsOrder);
	console.log(id);

	if (id) {
		return (
			<div className="order">
				<OrderMain></OrderMain>
				<Outlet
					context={{
						orderItems,
						id,
					}}
				/>
			</div>
		);
	}

	return (
		<div className="order">
			<OrderMain></OrderMain>
			<Outlet
				context={{
					orderItems: [],
					id: null,
				}}
			/>
		</div>
	);
};

export default OrderPage;
