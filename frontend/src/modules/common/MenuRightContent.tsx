import * as React from 'react';
// import MenuPayment from '../menu/MenuPayment';
import MenuOrderItem from '../menu/MenuOrderItem';
// import { PayPalScriptProvider } from '@paypal/react-paypal-js';
import MenuOrder from '@modules/menu/MenuOrder';
import { Link, useOutletContext } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import formatOrderItems from '@utils/formatOrderItems';

interface IMenuRightContentProps {
	children?: React.ReactNode;
}

// const orderItems = [
// 	{
// 		id: 1,
// 		name: 'Pizza 1',
// 		price: 10,
// 		quantity: 2,
// 		note: 'Extra cheese',
// 		image: './images/food-1.jpeg',
// 	},
// 	{
// 		id: 2,
// 		name: 'Pizza 2',
// 		price: 10,
// 		quantity: 2,
// 		note: 'Extra spicy',
// 		image: './images/food-2.jpeg',
// 	},
// 	{
// 		id: 3,
// 		name: 'Pizza 3',
// 		price: 14,
// 		quantity: 2,
// 		image: './images/food-3.jpeg',
// 	},
// ];

// const handleUnitTable = (tableName: string | number) => {
// 	return `#T${tableName}${Math.floor(Math.random() * 1000)}`;
// };

type ContextMenuItem = {
	id: string;
	tableName: string;
	isActive: boolean;
	onToggle: () => void;
};

const MenuRightContent: React.FunctionComponent<IMenuRightContentProps> = () => {
	const {
		id: orderId,
		isActive = false,
		onToggle,
		tableName = '',
	} = useOutletContext<ContextMenuItem>();

	const orderItems = useMenuItemsOrder((state) => state.menuOrder.menuItemsOrder);

	return (
		<>
			<button
				className={`fixed inset-0 w-full h-full bg-slate-500 bg-opacity-50 z-20 ${
					isActive ? 'opacity-100 visible' : 'opacity-0 invisible w-0 h-0'
				} transition-opacity menu-overlay`}
				onClick={onToggle}
			></button>
			<div className={`menu-right ${isActive ? 'active' : ''} transition-transform `}>
				<div className="menu-order">
					{orderId ? (
						<>
							<div className="menu-order-header">
								<div className="menu-order-header-item">
									<h4>Current Order</h4>
									<span>{`#${orderId.slice(-4)}`}</span>
								</div>
								<div className="menu-order-header-item">
									<h4>Table</h4>
									<span>{tableName}</span>
								</div>
							</div>
							<div className="menu-order-list">
								{orderItems.length === 0 ? (
									<div className="menu-order-empty">
										<h3>Order is empty</h3>
										<p>Please choose dishes to order</p>
									</div>
								) : (
									orderItems.map((item, index) => {
										return <MenuOrderItem key={`${item.dishId}${index}`} {...item}></MenuOrderItem>;
									})
								)}
							</div>
							{/* {hasPayment ? (
								<PayPalScriptProvider
									options={{
										'client-id':
											'AUv8rrc_P-EbP2E0mpb49BV7rFt3Usr-vdUZO8VGOnjRehGHBXkSzchr37SYF2GNdQFYSp72jh5QUhzG',
									}}
								>
									<MenuPayment orderItems={orderItems}></MenuPayment>
								</PayPalScriptProvider>
							) : (
							)} */}
							<MenuOrder></MenuOrder>
						</>
					) : (
						<div className="menu-order-empty">
							<h3>Please select a table</h3>
							<p>You must select a table before choosing dishes</p>
							<Link to={'/table'} className="flex items-center gap-3 max-w-[300px]  self-center">
								<i className="fa fa-arrow-left"></i>
								<span>Choose a table</span>
							</Link>
						</div>
					)}
				</div>
			</div>
		</>
	);
};

export default MenuRightContent;
