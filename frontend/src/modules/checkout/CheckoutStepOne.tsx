import orderApi from '@api/order';
import Button from '@components/button/Button';
import { usePayment } from '@stores/usePayment';
import { useQuery } from '@tanstack/react-query';
import * as React from 'react';
import { useParams } from 'react-router-dom';
import Swal from 'sweetalert2';
import OrderReceipt from './OrderReceipt';
import { IMenuOrderItem } from '@interfaces/index';
import LoadingCenter from '@modules/common/LoadingCenter';
import { usePaymentItems } from '@context/usePaymentItems';

interface ICheckoutStepOneProps {}

const CheckoutStepOne: React.FunctionComponent<ICheckoutStepOneProps> = (props) => {
	const nextStep = usePayment((state) => state.nextStep);
	const handleMoveToNextStep = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Are you definitely checked this order?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes',
		}).then((result) => {
			if (result.isConfirmed) {
				nextStep();
				Swal.fire('Success!', 'Please choose the method', 'success');
			}
		});
	};

	const {
		setPaymentItem,
		paymentItem: { items },
	} = usePaymentItems();

	const { orderId, step } = useParams();
	const [orderLineItems, setOrderLineItems] = React.useState<IMenuOrderItem[]>(items || []);
	console.log('🚀 ~ orderLineItems:', orderLineItems);
	const { data: order, isFetching } = useQuery({
		queryKey: ['order', orderId],
		queryFn: () => orderApi.getById(orderId),
		onSuccess: (data) => {
			if (data.code === 200) {
				setOrderLineItems(data.data?.orderLineItemResponseList || []);
				setPaymentItem({
					items: data.data?.orderLineItemResponseList || [],
					total: data.data?.grandTotal || 0,
					tableName: data.data?.tableName || '',
				});
			}
		},
	});

	if (!orderId) {
		return (
			<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
				<div className="flex flex-col w-full justify-center items-center">Order not found</div>
			</div>
		);
	}

	if (!step || step !== 'step-one') {
		return (
			<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
				<div className="flex flex-col w-full justify-center items-center">Step is not valid</div>
			</div>
		);
	}

	return (
		<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
			<div className="flex flex-col w-full">
				{isFetching && <LoadingCenter></LoadingCenter>}
				<OrderReceipt
					orderItems={orderLineItems}
					orderId={orderId}
					discount={order?.data?.discount || 0}
					tax={order?.data?.tax}
				></OrderReceipt>
			</div>
			<Button variant="primary" type="button" onClick={handleMoveToNextStep}>
				<div className="flex items-center gap-3">
					<span>Continue to payment</span>
					<i className="fa fa-arrow-right"></i>
				</div>
			</Button>
		</div>
	);
};

export default CheckoutStepOne;
