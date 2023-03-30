import kitchenApi from '@api/kitchen';
import CategoriesHeader from '@modules/common/CategoriesHeader';
import { useQuery } from '@tanstack/react-query';
import { categoriesItem } from 'constants/categoryMenuItem';
import * as React from 'react';

interface IMenuLeftHeaderProps {}

const MenuLeftHeader: React.FunctionComponent<IMenuLeftHeaderProps> = (props) => {
	const [categories, setCategories] = React.useState<
		{
			id: number | string;
			name: string;
			icon?: string | React.ReactNode;
			link?: string;
		}[]
	>(categoriesItem);

	useQuery({
		queryKey: ['categoriesItem'],
		queryFn: () => {
			return kitchenApi.getCategories();
		},
		onSuccess: (data) => {
			if (data.length > 0) {
				const newCategories = data.map((item) => {
					return {
						id: item.categoryId,
						name: item.categoryName,
						icon: item.icon,
						link: item.link,
					};
				});
				setCategories([
					{
						id: 'All',
						name: 'All',
						icon: 'fas fa-utensils',
						link: '#',
					},
					...newCategories,
				]);
			}
		},
	});
	return (
		<div className="menu-left-header">
			{/* <div className="menu-left-search">
				<input type="text" placeholder="Search for a menu" />
				<div className="menu-left-header-icon">
					<i className="fas fa-search"></i>
				</div>
			</div> */}
			<CategoriesHeader categories={categories}></CategoriesHeader>
		</div>
	);
};

export default MenuLeftHeader;
