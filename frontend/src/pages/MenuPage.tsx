import MenuLeftContent from '@modules/menu/MenuLeftContent';
import * as React from 'react';
import { Outlet, useParams } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import OrderCart from '@modules/menu/OrderCart';
import useToggleValue from '@hooks/useToggleValue';

interface IMenuPageProps {}

const MenuPage: React.FunctionComponent<IMenuPageProps> = () => {
	const { id } = useParams<{ id: string }>();
	const orderItems = useMenuItemsOrder((state) => state.menuItemsOrder);

	const totalItems = React.useCallback(() => {
		return orderItems.reduce((acc, item) => {
			return acc + item.quantity;
		}, 0);
	}, [orderItems]);

	const totalMoney = React.useCallback(() => {
		return orderItems.reduce((acc, item) => {
			return acc + item.price * item.quantity;
		}, 0);
	}, [orderItems]);

	const { value, handleToggleValue } = useToggleValue();

	if (id) {
		return (
			<div className="menu">
				<MenuLeftContent></MenuLeftContent>
				<OrderCart
					totalItem={totalItems()}
					totalMoney={totalMoney()}
					onToggle={() => handleToggleValue()}
				>
					<Outlet
						context={{
							orderItems,
							id,
							isActive: value,
							onToggle: () => handleToggleValue(),
						}}
					></Outlet>
				</OrderCart>
			</div>
		);
	}

	return (
		<div className="menu">
			<MenuLeftContent></MenuLeftContent>
			<Outlet
				context={{
					orderItems: [],
					id: null,
					isActive: value,
					onToggle: () => handleToggleValue(),
				}}
			></Outlet>
		</div>
	);
};

export default MenuPage;
