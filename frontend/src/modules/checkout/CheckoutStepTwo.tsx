import PaypalButton from '@components/button/PaypalButton';
import { usePaymentItems } from '@context/usePaymentItems';
import { usePayment } from '@stores/usePayment';
import * as React from 'react';
import { toast } from 'react-toastify';
import Swal from 'sweetalert2';
import {
	CreateOrderActions,
	CreateOrderData,
	OnApproveData,
	OnApproveActions,
} from '@paypal/paypal-js/types/components/buttons';
import { convertToUSD } from '@utils/formatCurrency';
import { ORDER_METHODS } from '@constants/orderMethods';
import Button from '@components/button/Button';

interface ICheckoutStepTwoProps {}
type orderMethods = 'cash' | 'credit_card' | 'e_wallet';
const CheckoutStepTwo: React.FunctionComponent<ICheckoutStepTwoProps> = (props) => {
	const nextStep = usePayment((state) => state.nextStep);
	const [orderMethod, setOrderMethod] = React.useState<orderMethods>('cash');
	const handleMoveToNextStep = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: `${
				orderMethod === 'cash'
					? 'Will you pay by cash?'
					: orderMethod === 'credit_card'
					? 'Will you pay by credit card?'
					: 'Will you pay by e-wallet?'
			}`,
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
		paymentItem: { items: orderItems },
	} = usePaymentItems();

	const handleChangeMethod = (methodId: orderMethods) => {
		setOrderMethod(methodId);
	};

	const handleShowUnavailableMethod = () => {
		toast.error(`This method is not available yet`);
	};

	const createOrder = (data: CreateOrderData, actions: CreateOrderActions) => {
		const purchase_units = orderItems.map((item) => {
			console.log(convertToUSD(item.price * item.quantity));
			const itemPrice = convertToUSD(item.price * item.quantity);
			return {
				description: item.note,
				reference_id: item.orderLineItemId?.toString(),
				amount: {
					value: itemPrice.toString(),
					currency_code: 'USD',
				},
			};
		});
		console.log(purchase_units);

		return actions.order.create({
			purchase_units,
		});
	};

	const onApprove = async (data: OnApproveData, actions: OnApproveActions) => {
		try {
			if (!actions.order) {
				toast.error('Something went wrong with your order');
				return;
			}

			const order = await actions.order.capture();
			console.log(order);
			toast.success('Your payment has been successfully processed');
		} catch (error) {
			toast.error('Something went wrong with your order');
			console.error(error);
		}
	};

	const onError = () => {
		toast.error('Something went wrong with your order');
	};

	const {
		paymentItem: { items, total, tableName },
	} = usePaymentItems();
	const convertAmount = React.useMemo(() => {
		return convertToUSD(total);
	}, [total]);

	return (
		<div className="flex items-center justify-center mt-20 gap-5 flex-col max-w-[500px] mx-auto">
			{ORDER_METHODS.map((method) => {
				return (
					<button
						className={`menu-order-method-item md:min-w-[200px] flex items-center justify-center ${
							orderMethod === method.id ? 'active' : ''
						}`}
						key={method.id}
						onClick={() =>
							method.isActive
								? handleChangeMethod(method.id as orderMethods)
								: handleShowUnavailableMethod()
						}
					>
						<i className={method.icon}></i>
						<span>{method.name}</span>
					</button>
				);
			})}
			<div className="flex items-center justify-center w-full">
				<Button
					type="button"
					className={`btn btn-primary w-full transition-all duration-300 ease-in-out h-0 ${
						orderMethod === 'cash' ? 'opacity-100 visible h-[48px] mt-5' : 'opacity-0 invisible'
					}`}
					onClick={handleMoveToNextStep}
					variant="primary"
				>
					Next
				</Button>
			</div>
			<div className="w-full">
				<PaypalButton
					amount={convertAmount.toString()}
					createOrder={createOrder}
					onError={onError}
					onApprove={onApprove}
					showSpinner={true}
					className={`${
						orderMethod === 'e_wallet' ? 'opacity-100 visible h-[48px] mt-5' : 'opacity-0 invisible'
					} transition-all duration-300 ease-in-out h-0 md:min-w-[200px] min-w-[100px]`}
				></PaypalButton>
			</div>
		</div>
	);
};

export default CheckoutStepTwo;
