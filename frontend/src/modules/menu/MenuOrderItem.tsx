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

	const removeItem = useMenuItemsOrder((state) => state.removeItem);

	const queryClient = useQueryClient();

	const handleIncrement = async (id: string | number) => {
		const { value: newQuantity } = await Swal.fire({
			title: 'Quantity',
			input: 'number',
			inputValue: quantity,
			showCancelButton: true,
			inputValidator: (value) => {
				if (!value) {
					return 'You need to write something!';
				}

				if (!Number.isInteger(Number(value))) {
					return 'Quantity must be a number';
				}

				if (+value < quantity) {
					return 'Quantity must be greater than current quantity';
				}
				return '';
			},
		});

		if (newQuantity && newQuantity !== quantity) {
			queryClient.invalidateQueries(['orderDetail', orderId]);
			Swal.fire('Updated!', 'Your item has been updated.', 'success');
		}
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
			// updateNoteItem(id as string, noteUpdate);
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
