import MenuLeftContent from '@modules/menu/MenuLeftContent';
import * as React from 'react';
import { Outlet, useParams } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import OrderCart from '@modules/menu/OrderCart';
import useToggleValue from '@hooks/useToggleValue';
import { useQuery } from '@tanstack/react-query';
import orderApi from '@api/order';

interface IMenuPageProps {}

const MenuPage: React.FunctionComponent<IMenuPageProps> = () => {
	const { id } = useParams<{ id: string }>();
	const setOrderItems = useMenuItemsOrder((state) => state.setMenuItemsOrder);

	const removeAllOrderItems = useMenuItemsOrder((state) => state.removeAll);

	const { value, handleToggleValue } = useToggleValue();

	if (id) {
		const { data: orderDetail } = useQuery({
			queryKey: ['orderDetail', id],
			queryFn: () => {
				return orderApi.getById(id);
			},
			onSuccess: (data) => {
				console.log(data?.data?.orderLineItemResponseList);
				removeAllOrderItems();
				if (data?.data?.orderLineItemResponseList !== undefined) {
					data.data.orderLineItemResponseList.forEach((item) => {
						setOrderItems(item);
					});
				}
			},
		});

		return (
			<div className="menu">
				<MenuLeftContent></MenuLeftContent>
				<OrderCart onToggle={() => handleToggleValue()}>
					<Outlet
						context={{
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
					id: null,
					isActive: value,
					onToggle: () => handleToggleValue(),
				}}
			></Outlet>
		</div>
	);
};

export default MenuPage;
