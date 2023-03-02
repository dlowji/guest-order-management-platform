import MenuLeftContent from '@modules/menu/MenuLeftContent';
import * as React from 'react';
import { Outlet, useParams } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';

interface IMenuPageProps {}

const MenuPage: React.FunctionComponent<IMenuPageProps> = () => {
	const { id } = useParams<{ id: string }>();
	const orderItems = useMenuItemsOrder((state) => state.menuItemsOrder);

	if (id) {
		return (
			<div className="menu">
				<MenuLeftContent></MenuLeftContent>
				<Outlet
					context={{
						orderItems,
						id,
					}}
				></Outlet>
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
				}}
			></Outlet>
		</div>
	);
};

export default MenuPage;
