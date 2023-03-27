import CategoriesHeader from '@modules/common/CategoriesHeader';
import MainContentHeader from '@modules/common/MainContentHeader';
import { orderCategoryItems } from 'constants/categoryOrderItem';
import * as React from 'react';
import OrderItem from './OrderItem';
import { useQuery } from '@tanstack/react-query';
import orderApi from '@api/order';
import LoadingCenter from '@modules/common/LoadingCenter';
import { useQueryString } from '@utils/queryString';

interface IOrderMainProps {}

const OrderMain: React.FunctionComponent<IOrderMainProps> = () => {
	const [activeOrder, setActiveOrder] = React.useState<string>('#456644');
	const { q: status } = useQueryString();
	const { data: orderItems, isFetching } = useQuery({
		queryKey: ['order', status],
		queryFn: () => {
			return orderApi.getAll(status);
		},
	});

	return (
		<div className="order-left">
			<CategoriesHeader categories={orderCategoryItems} className="!mt-[10px]"></CategoriesHeader>
			<MainContentHeader title="Current orders" quantity="10 orders"></MainContentHeader>
			<div className="order-list">
				{isFetching && <LoadingCenter />}

				{orderItems?.data?.length === 0 && (
					<div className="flex items-center justify-center w-full h-full">
						<p className="text-2xl font-semibold text-gray-500">No orders available</p>
					</div>
				)}

				{orderItems?.data &&
					orderItems.data.map((item, index) => {
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
