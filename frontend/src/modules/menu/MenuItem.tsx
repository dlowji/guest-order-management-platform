import { IMenuItem } from '@interfaces/index';
import { useBearStore } from '@stores/useBearStore';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';

interface IMenuItemProps extends IMenuItem {}

const MenuItem: React.FunctionComponent<IMenuItemProps> = ({
	id,
	image,
	title,
	price,
	status,
	icon,
}) => {
	if (status.toLowerCase() === 'available') {
		icon += ' !text-green-500';
	}

	if (status.toLowerCase() === 'best seller') {
		icon += ' !text-yellow-500';
	}

	if (status.toLowerCase() === 'must try') {
		icon += ' !text-red-500';
	}

	const addToCart = useMenuItemsOrder((state) => state.setMenuItemsOrder);

	const { id: idParams } = useParams<{ id: string }>();

	const handleAddToCard = (id: string | number) => {
		if (!idParams) {
			toast.error('Please choose a table first');
			return;
		}
		addToCart({
			id,
			image,
			name: title,
			price,
			quantity: 1,
		});
	};

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
					<h4>{price}</h4>
				</div>
				<div className="menu-item-status">
					<span>{status}</span>
					{icon && <i className={icon}></i>}
				</div>
				<button className="menu-item-button" onClick={() => handleAddToCard(id)}>
					Add to cart
				</button>
			</div>
		</div>
	);
};

export default MenuItem;
