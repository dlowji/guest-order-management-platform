import CategoriesHeader from '@modules/common/CategoriesHeader';
import MainContentHeader from '@modules/common/MainContentHeader';
import { orderCategoryItems } from 'constants/categoryOrderItem';
import * as React from 'react';
import FAKE_ITEM from 'data/orderContentItems.json';
import OrderItem from './OrderItem';

interface IOrderMainProps {}

const OrderMain: React.FunctionComponent<IOrderMainProps> = () => {
	const [activeOrder, setActiveOrder] = React.useState<string>('#456644');
	return (
		<div className="order-left">
			<CategoriesHeader categories={orderCategoryItems} className="!mt-[10px]"></CategoriesHeader>
			<MainContentHeader title="Current orders" quantity="10 orders"></MainContentHeader>
			<div className="order-list">
				{FAKE_ITEM.map((item, index) => {
					return (
						<OrderItem
							key={index}
							item={item}
							active={activeOrder === item.orderId}
							onClick={() => setActiveOrder(item.orderId)}
						></OrderItem>
					);
				})}
			</div>
		</div>
	);
};

export default OrderMain;
