/* eslint-disable jsx-a11y/no-static-element-interactions */
import { TOrder } from '@customTypes/index';
import { formatDatetime } from '@utils/formatDatetime';
import * as React from 'react';
import { Link } from 'react-router-dom';

interface IOrderItemProps {
	item: TOrder;
	active?: boolean;
	onClick?: () => void;
}

const OrderItem: React.FunctionComponent<IOrderItemProps> = ({
	item: { orderId, createdAt, grandTotal, orderStatus },
	active = false,
	onClick,
}) => {
	const statusColor = React.useMemo(() => {
		if (orderStatus === 'CREATED') return 'created';
		if (orderStatus === 'IN_PROCESSING') return 'in-processing';
		if (orderStatus === 'CANCELED') return 'canceled';
		return 'completed';
	}, [orderStatus, active]);

	return (
		<Link
			to={`/menu/order/${orderId}`}
			className={`order-item ${active ? 'active' : ''} ${statusColor}`}
			onClick={onClick}
		>
			<div className="order-item-top">
				<h3 className="order-item-title">Order: {orderId.slice(-4)}</h3>
				<span className="order-item-time">{formatDatetime(createdAt, 'HH:mm:ss')}</span>
			</div>
			<div className="order-item-bottom">
				<div className="order-item-bottom-left">
					{/* <div className="order-item-table">Table: {tableId}</div> */}
					{/* <div className="order-item-quantity">Quantity: {quantity}</div> */}
				</div>
				<div className="order-item-bottom-right">
					<div className="order-item-price">${grandTotal}</div>
					<div className={`order-item-status`}>{orderStatus}</div>
				</div>
			</div>
		</Link>
	);
};

export default OrderItem;
