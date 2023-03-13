import CategoriesHeader from '@modules/common/CategoriesHeader';
import { categoriesItem } from 'constants/categoryMenuItem';
import * as React from 'react';

interface IMenuLeftHeaderProps {}

const MenuLeftHeader: React.FunctionComponent<IMenuLeftHeaderProps> = (props) => {
	return (
		<div className="menu-left-header">
			{/* <div className="menu-left-search">
				<input type="text" placeholder="Search for a menu" />
				<div className="menu-left-header-icon">
					<i className="fas fa-search"></i>
				</div>
			</div> */}
			<CategoriesHeader categories={categoriesItem}></CategoriesHeader>
		</div>
	);
};

export default MenuLeftHeader;
