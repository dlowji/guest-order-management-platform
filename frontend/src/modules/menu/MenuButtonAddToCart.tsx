import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';

interface IMenuButtonItemProps {
	dishId: string;
	image: string;
	title: string;
	price: number;
}

const MenuButtonAddToCart: React.FunctionComponent<IMenuButtonItemProps> = ({
	dishId,
	image,
	title,
	price,
}) => {
	const { id: idParams } = useParams<{ id: string }>();
	const addToCart = useMenuItemsOrder((state) => state.addToOrder);
	const handleAddToCard = () => {
		if (!idParams) {
			toast.error('Please choose a table first');
			return;
		}
		addToCart({
			dishId,
			quantity: 1,
			price,
			image,
			note: '',
			title,
		});
	};
	return (
		<button className="menu-item-button" onClick={handleAddToCard}>
			Add to cart
		</button>
	);
};

export default MenuButtonAddToCart;
