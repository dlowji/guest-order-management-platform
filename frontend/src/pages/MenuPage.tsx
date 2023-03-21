import MenuLeftContent from '@modules/menu/MenuLeftContent';
import * as React from 'react';
import { Outlet, useParams } from 'react-router-dom';
import OrderCart from '@modules/menu/OrderCart';
import useToggleValue from '@hooks/useToggleValue';
import { useQuery } from '@tanstack/react-query';
import orderApi from '@api/order';
import LoadingCenter from '@modules/common/LoadingCenter';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';

interface IMenuPageProps {}

const MenuPage: React.FunctionComponent<IMenuPageProps> = () => {
	const { id } = useParams<{ id: string }>();
	const setOrderItems = useMenuItemsOrder((state) => state.setMenuOrder);
	const { value, handleToggleValue } = useToggleValue();

	const { data: orderDetail, isFetching } = useQuery({
		queryKey: ['orderDetail', id],
		queryFn: () => {
			return orderApi.getById(id);
		},
		onSuccess: (data) => {
			if (data?.data?.orderLineItemResponseList !== undefined) {
				const orderItems = [...data.data.orderLineItemResponseList];
				console.log('orderItems', orderItems);
				if (orderItems.length > 0) {
					const menuItemsOrder = orderItems.map((item) => {
						return {
							orderLineItemId: item.orderLineItemId,
							dishId: item.dishId,
							title: item.title,
							price: item.price,
							quantity: item.quantity,
							image: item.image,
							note: item.note,
						};
					});
					setOrderItems({
						orderId: id as string,
						menuItemsOrder: [...menuItemsOrder],
						method: 'POST',
					});
				}
			}
		},
	});

	return (
		<div className="menu">
			<MenuLeftContent></MenuLeftContent>

			{isFetching && <LoadingCenter />}

			{id && (
				<OrderCart onToggle={() => handleToggleValue()}>
					<Outlet
						context={{
							id,
							tableName: orderDetail?.data?.tableName,
							isActive: value,
							onToggle: () => handleToggleValue(),
						}}
					></Outlet>
				</OrderCart>
			)}
		</div>
	);
};

export default MenuPage;
