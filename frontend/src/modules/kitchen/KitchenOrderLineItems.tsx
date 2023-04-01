import { IMenuOrderItem } from '@interfaces/index';
import * as React from 'react';
import 'react-step-progress-bar/styles.css';
import KitchenOrderLineItem from './KitchenOrderLineItem';
import { useOrderDetail } from '@context/useOrderDetail';
import Button from '@components/button/Button';
import { toast } from 'react-toastify';
import Swal from 'sweetalert2';
import orderApi from '@api/order';
import { useNavigate } from 'react-router-dom';
import { useQueryClient } from '@tanstack/react-query';
import { OrderLineItemStatusResponse } from '@constants/orderLineItemStatus';
import kitchenApi from '@api/kitchen';
interface IKitchenOrderLineItemsProps {
	items: IMenuOrderItem[];
}

const KitchenOrderLineItems: React.FunctionComponent<IKitchenOrderLineItemsProps> = ({
	items = [],
}) => {
	const { totalAccept, orderLineItems, orderDetail } = useOrderDetail();
	const isNewOrder = React.useMemo(() => {
		return orderDetail?.orderStatus === 'CREATED';
	}, [orderDetail?.orderStatus]);

	const isProcessing = React.useMemo(() => {
		return orderDetail?.orderStatus === 'IN_PROCESSING';
	}, [orderDetail?.orderStatus]);

	const navigate = useNavigate();
	const queryClient = useQueryClient();

	const handleStartCook = () => {
		if (totalAccept < orderLineItems.length) {
			toast.error('You must choose which dishes to cook or cancel!');
			return;
		}

		const orderId = orderDetail?.orderId;
		if (!orderId) {
			toast.error('Order id is not found!');
			return;
		}

		Swal.fire({
			title: 'Are you sure?',
			text: 'You will start cooking dishes!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, start cook!',
		}).then(async (result) => {
			if (result.isConfirmed) {
				const response = await orderApi.progressOrder(orderId, orderLineItems);
				if (response.code === 200) {
					toast.success('Start cooking dishes!');
					queryClient.refetchQueries(['order', orderId]);
					queryClient.refetchQueries(['order', 'in_processing']);
					navigate(`/kitchen?q=in_processing`);
				} else {
					toast.error(response.message || 'Something went wrong!');
				}
			}
		});
	};

	const handleMarkDone = () => {
		const orderId = orderDetail?.orderId;
		if (!orderId) {
			toast.error('Order id is not found!');
			return;
		}

		Swal.fire({
			title: 'Are you sure?',
			text: 'This order will be marked as done!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, mark done!',
		}).then(async (result) => {
			if (result.isConfirmed) {
				const response = await kitchenApi.markDoneOrder(orderId, orderLineItems);
				if (response.code === 200) {
					toast.success('Order marked as done!');
					queryClient.refetchQueries(['order', 'in_processing']);
					queryClient.refetchQueries(['order', orderId]);
					navigate('/kitchen?q=in_processing');
				} else {
					toast.error(response.message || 'Something went wrong!');
				}
			}
		});
	};

	if (items.length === 0) {
		return (
			<div className="md:max-w-[800px] mx-auto my-10">
				<div className="text-center text:2xl md:text-4xl font-bold">No dishes to cook!</div>
			</div>
		);
	}

	return (
		<div className="md:max-w-[800px] mx-auto mb-10">
			{items.map((item) => {
				if (item.orderLineItemStatus === OrderLineItemStatusResponse.STOCK_OUT) {
					return null;
				}
				return <KitchenOrderLineItem key={item.orderLineItemId} item={item}></KitchenOrderLineItem>;
			})}
			{isNewOrder && (
				<div className="flex justify-end mt-5 items-center gap-3">
					<div className="text-gray-500 text-lg font-bold">Total accept: {totalAccept}</div>
					<Button type="button" variant="primary" onClick={handleStartCook}>
						Start cook
					</Button>
				</div>
			)}
			{isProcessing && (
				<div className="flex justify-end mt-5 items-center gap-3">
					<div className="text-gray-500 text-lg font-bold">Total accept: {totalAccept}</div>
					<Button type="button" variant="primary" onClick={handleMarkDone}>
						Mark done
					</Button>
				</div>
			)}
		</div>
	);
};

export default KitchenOrderLineItems;
