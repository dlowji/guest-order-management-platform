import * as React from 'react';
import MenuLeftHeader from './MenuLeftHeader';
import MainContentHeader from '@modules/common/MainContentHeader';
import MenuList from './MenuList';
import MenuItem from './MenuItem';
import { useMenuItems } from '@stores/useMenuItems';

interface IMenuLeftContentProps {
	children?: React.ReactNode;
}

const MenuLeftContent: React.FunctionComponent<IMenuLeftContentProps> = () => {
	const { menuItems } = useMenuItems();

	return (
		<div className="menu-left">
			<MenuLeftHeader></MenuLeftHeader>
			<MainContentHeader
				title="Choose topping"
				quantity={`${menuItems.length} items`}
			></MainContentHeader>
			<MenuList>
				{menuItems.map((item) => (
					<MenuItem
						key={item.id}
						id={item.id}
						image={item.image}
						title={item.title}
						price={item.price}
						status={item.status}
						icon={item.icon}
					/>
				))}
			</MenuList>
		</div>
	);
};

export default MenuLeftContent;
