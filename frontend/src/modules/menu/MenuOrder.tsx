import Button from '@components/button/Button';
import * as React from 'react';
import MenuOrderCalculate from './MenuOrderCalculate';
import { useNavigate, useParams } from 'react-router-dom';
import Swal from 'sweetalert2';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import orderApi from '@api/order';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';

interface IMenuOrderProps {}

const MenuOrder: React.FunctionComponent<IMenuOrderProps> = () => {
	const { id } = useParams<{ id: string }>();

	const navigate = useNavigate();

	const orderItems = useMenuItemsOrder((state) => state.menuOrder.menuItemsOrder);

	const queryClient = useQueryClient();
	const { mutate: updateOrderLineItems } = useMutation({
		mutationFn: () => {
			return orderApi.updateOrderLineItems(id, orderItems);
		},
		onSuccess: (data) => {
			if (data.code === 200) {
				queryClient.invalidateQueries(['orderDetail', id]);
				Swal.fire('Ordered!', 'Your order has been sent to the kitchen.', 'success');
				return;
			} else {
				Swal.fire('Error!', data?.message || 'Error! Please try again later', 'error');
			}
		},
		onError: (data) => {
			console.log(data);
			Swal.fire('Error!', 'Something went wrong.', 'error');
		},
	});

	const handleOrder = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Your action will send your order to the kitchen!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			cancelButtonText: 'No, cancel!',
			confirmButtonText: 'Yes, order now!',
		}).then((result) => {
			if (result.isConfirmed) {
				updateOrderLineItems();
			}
		});
	};

	const handleCheckOut = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Do you want to checkout your order?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			cancelButtonText: 'No, cancel!',
			confirmButtonText: 'Yes, checkout!',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire('Checked out!', 'Your order has been checked out.', 'success');
				navigate(`/checkout/${id}/step-one`);
			}
		});
	};

	const handleCancel = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Your action will remove all items from your order!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, remove it!',
		}).then((result) => {
			if (result.isConfirmed) {
				if (id) {
					Swal.fire('Deleted!', 'Your items have been removed.', 'success');
					navigate(`/table`);
				} else {
					Swal.fire('Error!', 'Something went wrong.', 'error');
				}
			}
		});
	};

	return (
		<div className=" mt-auto p-5 -mx-5">
			<MenuOrderCalculate></MenuOrderCalculate>
			<div className="grid grid-cols-2 gap-3">
				<Button type="button" variant="secondary" onClick={() => handleCancel()}>
					Cancel order
				</Button>
				<Button type="button" onClick={() => handleOrder()}>
					Order now
				</Button>
				<Button
					type="button"
					variant="primary"
					className="col-span-2 bg-slate-300"
					onClick={() => handleCheckOut()}
				>
					<i className="fa fa-check"></i>
					<span className="ml-2">Checkout</span>
				</Button>
			</div>
		</div>
	);
};

export default MenuOrder;
