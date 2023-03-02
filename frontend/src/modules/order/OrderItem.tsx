/* eslint-disable jsx-a11y/no-static-element-interactions */
import { TStatusTable } from '@customTypes/index';
import * as React from 'react';
import { Link } from 'react-router-dom';

interface IOrderItem {
	orderId: string;
	timeStart: string;
	table: string;
	quantity: number;
	price: number;
	status: TStatusTable | string;
}

interface IOrderItemProps {
	item: IOrderItem;
	active?: boolean;
	onClick?: () => void;
}

const OrderItem: React.FunctionComponent<IOrderItemProps> = ({
	item: { orderId, timeStart, table, quantity, price, status },
	active = false,
	onClick,
}) => {
	const statusColor = React.useMemo(() => {
		if (status === 'ordered') return 'ordered';
		if (status === 'occupied') return 'dineIn';
		return 'free';
	}, [status, active]);

	return (
		<Link
			to={`/order/${orderId}`}
			className={`order-item ${active ? 'active' : ''} ${statusColor}`}
			onClick={onClick}
		>
			<div className="order-item-top">
				<h3 className="order-item-title">Order: {orderId}</h3>
				<span className="order-item-time">{timeStart}</span>
			</div>
			<div className="order-item-bottom">
				<div className="order-item-bottom-left">
					<div className="order-item-table">Table: {table}</div>
					<div className="order-item-quantity">Quantity: {quantity}</div>
				</div>
				<div className="order-item-bottom-right">
					<div className="order-item-price">${price}</div>
					<div className={`order-item-status`}>{status}</div>
				</div>
			</div>
		</Link>
	);
};

export default OrderItem;
