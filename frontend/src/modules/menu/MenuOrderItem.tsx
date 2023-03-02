import { IMenuOrderItem } from '@interfaces/index';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import * as React from 'react';
import Swal from 'sweetalert2';

interface IMenuOrderItemProps extends IMenuOrderItem {}

const MenuOrderItem: React.FunctionComponent<IMenuOrderItemProps> = ({
	id,
	name,
	price = 0,
	quantity = 1,
	note = 'nothing',
	image,
}) => {
	const decrement = useMenuItemsOrder((state) => state.decrement);

	const increment = useMenuItemsOrder((state) => state.increment);

	const removeItem = useMenuItemsOrder((state) => state.removeItem);

	const updateNoteItem = useMenuItemsOrder((state) => state.updateNote);
	const handleDecrement = (id: string | number) => {
		decrement(id);
	};

	const handleIncrement = (id: string | number) => {
		increment(id);
	};

	const handleRemoveItem = (id: string | number) => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Your action will remove this item from your order!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, remove it!',
		}).then((result) => {
			if (result.isConfirmed) {
				removeItem(id);
				Swal.fire('Deleted!', 'Your item has been removed.', 'success');
			}
		});
	};

	const handleUpdateNote = async (id: string | number) => {
		const { value: noteUpdate } = await Swal.fire({
			title: 'Update Note',
			input: 'text',
			inputValue: note,
			showCancelButton: true,
			inputValidator: (value) => {
				if (!value) {
					return 'You need to write something!';
				}
				return '';
			},
		});

		if (noteUpdate && noteUpdate !== 'nothing') {
			updateNoteItem(id, noteUpdate);
		}
	};

	return (
		<div className="menu-order-item">
			<div className="menu-order-item-image">
				<img srcSet={`${image} 4x`} alt="food" />
			</div>
			<div className="menu-order-item-content">
				<div className="menu-order-item-title">
					<h4>{name}</h4>
				</div>
				<div className="menu-order-item-note">
					<p>Note: {note}</p>
				</div>
				<div className="menu-order-item-price">
					<h4>${price}</h4>
				</div>
				<div className="menu-order-item-btn">
					<div className="menu-order-item-add">
						<button className="menu-order-item-btn-minus" onClick={() => handleDecrement(id)}>
							<i className="fas fa-minus"></i>
						</button>
						<span className="menu-order-item-quantity">{quantity}</span>
						<button className="menu-order-item-btn-plus" onClick={() => handleIncrement(id)}>
							<i className="fas fa-plus"></i>
						</button>
					</div>
					<div className="menu-order-item-edit">
						<button className="menu-order-item-btn-edit" onClick={() => handleUpdateNote(id)}>
							<i className="fas fa-edit"></i>
						</button>
						<button className="menu-order-item-btn-delete" onClick={() => handleRemoveItem(id)}>
							<i className="fas fa-trash"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	);
};

export default MenuOrderItem;
