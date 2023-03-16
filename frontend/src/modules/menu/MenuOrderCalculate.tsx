import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import { formatCurrency } from '@utils/formatCurrency';
import * as React from 'react';

interface IMenuOrderCalculateProps {}

const MenuOrderCalculate: React.FunctionComponent<IMenuOrderCalculateProps> = () => {
	const orderItems = useMenuItemsOrder((state) => state.menuOrder.menuItemsOrder);

	const totalPrice = React.useMemo(() => {
		return orderItems.reduce((acc, item) => {
			return acc + item.price * item.quantity;
		}, 0);
	}, [orderItems]);

	return (
		<div className="menu-order-top">
			<div className="menu-order-top-calculate">
				{orderItems.map((item, index) => {
					return (
						<div className="menu-order-top-calculate-item" key={`${item.dishId}${index}`}>
							<h4>
								{item.title} {item.quantity ? `x${item.quantity}` : ``}
							</h4>
							<span>{formatCurrency(item.price)}</span>
						</div>
					);
				})}
			</div>
			{totalPrice ? (
				<div className="menu-order-top-total">
					<h4>Total</h4>
					<span>{formatCurrency(totalPrice)}</span>
				</div>
			) : null}
		</div>
	);
};

export default MenuOrderCalculate;
