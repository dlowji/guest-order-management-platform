import { IMenuOrderItem } from '@interfaces/index';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import { useQueryClient } from '@tanstack/react-query';
import { formatCurrency } from '@utils/formatCurrency';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import Swal from 'sweetalert2';

interface IMenuOrderItemProps extends IMenuOrderItem {}

const MenuOrderItem: React.FunctionComponent<IMenuOrderItemProps> = ({
	dishId,
	title,
	price = 0,
	quantity = 1,
	note = 'nothing',
	image,
}) => {
	const { id: orderId } = useParams<{ id: string }>();

	if (!orderId)
		return (
			<div className="text-center">
				<p className="text-2xl text-primaryff">Order id is not found</p>
			</div>
		);

	const decrement = useMenuItemsOrder((state) => state.decrement);

	const increment = useMenuItemsOrder((state) => state.increment);

	const removeItem = useMenuItemsOrder((state) => state.removeItem);

	const updateNoteItem = useMenuItemsOrder((state) => state.updateNote);
	const handleDecrement = (id: string | number) => {
		decrement(id as string);
	};

	const queryClient = useQueryClient();

	const handleIncrement = (id: string | number) => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Do you want to add these items to your order?',
			icon: 'question',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, add it!',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire('Added!', 'Your item has been added.', 'success');
				queryClient.invalidateQueries(['orderDetail', id]);
			}
		});
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
				removeItem(id as string);
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
			updateNoteItem(id as string, noteUpdate);
		}
	};

	return (
		<div className="menu-order-item">
			<div className="menu-order-item-image">
				<img srcSet={`${image} 4x`} alt="food" />
			</div>
			<div className="menu-order-item-content">
				<div className="menu-order-item-title">
					<h4>{title}</h4>
				</div>
				<div className="menu-order-item-note">
					<p>Note: {note}</p>
				</div>
				<div className="menu-order-item-price">
					<h4>{formatCurrency(price)}</h4>
				</div>
				<div className="menu-order-item-btn">
					<div className="menu-order-item-add">
						{/* <button className="menu-order-item-btn-minus" onClick={() => handleDecrement(dishId)}>
							<i className="fas fa-minus"></i>
						</button> */}
						<span className="menu-order-item-quantity">{quantity}</span>
						<button className="menu-order-item-btn-plus" onClick={() => handleIncrement(dishId)}>
							<i className="fas fa-plus"></i>
						</button>
					</div>
					<div className="menu-order-item-edit">
						<button className="menu-order-item-btn-edit" onClick={() => handleUpdateNote(dishId)}>
							<i className="fas fa-edit"></i>
						</button>
						<button className="menu-order-item-btn-delete" onClick={() => handleRemoveItem(dishId)}>
							<i className="fas fa-trash"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	);
};

export default MenuOrderItem;
