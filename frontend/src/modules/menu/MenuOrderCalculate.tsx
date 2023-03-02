import { IMenuOrderItem } from '@interfaces/index';
import * as React from 'react';

interface IMenuOrderCalculateProps {
	orderItems: IMenuOrderItem[];
}

const MenuOrderCalculate: React.FunctionComponent<IMenuOrderCalculateProps> = ({
	orderItems = [],
}) => {
	const totalPrice = React.useMemo(() => {
		return orderItems.reduce((acc, item) => {
			return acc + item.price * item.quantity;
		}, 0);
	}, [orderItems]);

	return (
		<div className="menu-order-top">
			<div className="menu-order-top-calculate">
				{orderItems.map((item) => {
					return (
						<div className="menu-order-top-calculate-item" key={item.id}>
							<h4>
								{item.name} {item.quantity ? `x${item.quantity}` : ``}
							</h4>
							<span>${item.price}</span>
						</div>
					);
				})}
			</div>
			{totalPrice ? (
				<div className="menu-order-top-total">
					<h4>Total</h4>
					<span>${totalPrice}</span>
				</div>
			) : null}
		</div>
	);
};

export default MenuOrderCalculate;
