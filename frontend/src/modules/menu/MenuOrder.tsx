import Button from '@components/button/Button';
import * as React from 'react';
import MenuOrderCalculate from './MenuOrderCalculate';
import { IMenuOrderItem } from '@interfaces/index';
import { useNavigate, useParams } from 'react-router-dom';
import { useMenuItemsOrder } from '@stores/useMenuItemsOrder';
import Swal from 'sweetalert2';

interface IMenuOrderProps {
	orderItems: IMenuOrderItem[];
}

const MenuOrder: React.FunctionComponent<IMenuOrderProps> = ({ orderItems = [] }) => {
	const { id } = useParams<{ id: string }>();
	const removeAll = useMenuItemsOrder((state) => state.removeAll);

	const navigate = useNavigate();
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
				Swal.fire('Ordered!', 'Your order has been sent to the kitchen.', 'success');
				navigate(`/order/${id}`);
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
				removeAll();
				Swal.fire('Deleted!', 'Your items have been removed.', 'success');
				navigate(`/table`);
			}
		});
	};

	return (
		<div className=" mt-auto p-5 -mx-5">
			<MenuOrderCalculate orderItems={orderItems}></MenuOrderCalculate>
			<div className="grid grid-cols-2 gap-3">
				<Button type="button" variant="secondary" onClick={() => handleCancel()}>
					Cancel
				</Button>
				<Button type="button" onClick={() => handleOrder()}>
					Order now
				</Button>
			</div>
		</div>
	);
};

export default MenuOrder;
