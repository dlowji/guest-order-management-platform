import { IMenuItem } from '@interfaces/index';
import { useAuth } from '@stores/useAuth';
import { formatCurrency } from '@utils/formatCurrency';
import * as React from 'react';
import MenuButtonAddToCart from './MenuButtonAddToCart';
import MenuButtonToggle from './MenuButtonToggle';
import Role from '@constants/ERole';

interface IMenuItemProps extends IMenuItem {}

const MenuItem: React.FunctionComponent<IMenuItemProps> = ({
	id,
	image,
	title,
	price,
	status,
	icon,
}) => {
	const iconClassnames = React.useMemo(() => {
		if (status.toLowerCase() === 'available') {
			return `${icon} !text-green-500`;
		}

		if (status.toLowerCase() === 'best seller') {
			return `${icon} !text-yellow-500`;
		}

		if (status.toLowerCase() === 'must try') {
			return `${icon} !text-red-500`;
		}
		return icon;
	}, [status]);

	const user = useAuth((state) => state.user);

	const roleName = user?.roleName;
	const isEmployee = React.useMemo(() => {
		if ((roleName && roleName === Role.ADMIN) || roleName === Role.EMPLOYEE) {
			return true;
		}
		return false;
	}, [roleName]);

	const reFormatStatus = React.useMemo(() => {
		if (status.toLowerCase() === 'AVAILABLE') {
			return 'AVAILABLE';
		}
		if (status.toLowerCase() === 'UN_AVAILABLE') {
			return 'UN AVAILABLE';
		}
		return status;
	}, [status]);

	return (
		<div className="menu-item">
			<div className="menu-item-image">
				<img srcSet={`${image} 2x`} alt="food" />
			</div>
			<div className="menu-item-title">
				<h4>{title}</h4>
			</div>
			<div className="flex flex-col mt-auto items-center gap-[10px]">
				<div className="menu-item-price">
					<h4>{formatCurrency(price)}</h4>
				</div>
				<div className="menu-item-status">
					<span>{reFormatStatus}</span>
					{iconClassnames && <i className={iconClassnames}></i>}
				</div>
				{isEmployee ? (
					<MenuButtonAddToCart
						dishId={id}
						image={image}
						title={title}
						price={price}
					></MenuButtonAddToCart>
				) : (
					<MenuButtonToggle isAvailable={status === 'AVAILABLE'} dishId={id}></MenuButtonToggle>
				)}
			</div>
		</div>
	);
};

export default MenuItem;
