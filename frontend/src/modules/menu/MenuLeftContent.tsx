import * as React from 'react';
import MenuLeftHeader from './MenuLeftHeader';
import MainContentHeader from '@modules/common/MainContentHeader';
import MenuList from './MenuList';
import MenuItem from './MenuItem';
import { useMenuItems } from '@stores/useMenuItems';
import { useQuery } from '@tanstack/react-query';
import kitchenApi from '@api/kitchen';
import CircleLoading from '@components/loading/CircleLoading';
import { TDish } from '@customTypes/index';
import { useSearchParams } from 'react-router-dom';
import { useQueryString } from '@utils/queryString';

interface IMenuLeftContentProps {
	children?: React.ReactNode;
}

const MenuLeftContent: React.FunctionComponent<IMenuLeftContentProps> = () => {
	const { setMenuItems } = useMenuItems();
	const { q: category } = useQueryString();

	const {
		data: menuItems,
		isError,
		isFetching,
	} = useQuery({
		queryKey: ['menuItems', category],
		queryFn: () => {
			return kitchenApi.getDishes(category);
		},
		onSuccess: (data) => {
			data.forEach((item: TDish) => {
				setMenuItems(item);
			});
		},
	});

	return (
		<div className="menu-left">
			<MenuLeftHeader></MenuLeftHeader>
			{isFetching && (
				<div className="flex items-center justify-center w-full">
					<CircleLoading color="#ff7200"></CircleLoading>
				</div>
			)}

			{!isFetching && !isError && menuItems && (
				<>
					<MainContentHeader
						title="Choose topping"
						quantity={`${menuItems.length} items`}
					></MainContentHeader>
					{menuItems.length <= 0 ? (
						<div className="flex items-center justify-center">
							<p className="text-2xl text-center font-bold">No items found</p>
						</div>
					) : (
						<MenuList>
							{menuItems.map((item) => (
								<MenuItem
									key={item.dishId}
									id={item.dishId}
									image={item.image}
									title={item.title}
									price={item.price}
									status={item.dishStatus}
								/>
							))}
						</MenuList>
					)}
				</>
			)}
		</div>
	);
};

export default MenuLeftContent;
