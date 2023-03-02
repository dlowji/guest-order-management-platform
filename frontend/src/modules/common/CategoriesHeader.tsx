import * as React from 'react';
import { NavLink, useLocation } from 'react-router-dom';

interface ICategoryItem {
	id: number;
	name: string;
	icon?: string | React.ReactNode;
	link?: string;
}

interface ICategoriesHeaderProps {
	categories: ICategoryItem[];
	className?: string;
}

const CategoriesHeader: React.FunctionComponent<ICategoriesHeaderProps> = ({
	categories,
	className = '',
}) => {
	const location = useLocation();

	const query = React.useMemo(() => {
		const re = new RegExp(/^\?q=*/);
		return location.search.replace(re, '');
	}, [location.search]);
	const [activeCategory, setActiveCategory] = React.useState<string>(query);

	React.useEffect(() => {
		if (!query) {
			setActiveCategory('all');
		} else {
			setActiveCategory(query);
		}
	}, [query]);

	return (
		<div className={`categories ${className}`}>
			<div className="categories-list">
				{categories.map((category) => {
					return (
						<NavLink
							to={category.link ? category.link : '/'}
							className={() =>
								activeCategory === category.name.toLowerCase()
									? `categories-item categories-item-active`
									: `categories-item`
							}
							key={category.id}
						>
							{category.icon && (
								<div className="categories-item-icon">
									{typeof category.icon === 'string' ? (
										<i className={category.icon}></i>
									) : (
										category.icon
									)}
								</div>
							)}
							<div className="categories-item-title">
								<h4>{category.name}</h4>
							</div>
						</NavLink>
					);
				})}
			</div>
		</div>
	);
};

export default CategoriesHeader;
