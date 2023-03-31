import { checkoutSteps } from '@constants/checkoutSteps';
import { PaymentItemsProvider } from '@context/usePaymentItems';
import MultiStep from '@modules/checkout/MultiStep';
import TopnavCheckout from '@modules/checkout/TopnavCheckout';
import { PayPalScriptProvider } from '@paypal/react-paypal-js';
import { usePayment } from '@stores/usePayment';
import * as React from 'react';
import { useNavigate, useParams } from 'react-router-dom';

interface ICheckoutPageProps {}

const CheckoutPage: React.FunctionComponent<ICheckoutPageProps> = () => {
	const { orderId, step } = useParams<{
		orderId: string;
		step?: string;
	}>();
	const navigate = useNavigate();
	const setPayment = usePayment((state) => state.setPayment);
	const payment = usePayment((state) => state.payment);

	React.useEffect(() => {
		if (!payment?.currentStep) {
			setPayment({
				orderId: orderId as string,
				currentStep: 0,
				totalSteps: checkoutSteps.length,
			});
			navigate(`/checkout/${orderId}/${checkoutSteps[0].link}`);
			return;
		}

		if (payment.currentStep === payment.totalSteps - 1) {
			navigate(`/checkout/${orderId}/success`);
		}

		if (payment.currentStep === 0) {
			navigate(`/checkout/${orderId}/${checkoutSteps[0].link}`);
		}

		if (payment.currentStep > 0 && payment.currentStep < payment.totalSteps - 1) {
			navigate(`/checkout/${orderId}/${checkoutSteps[payment.currentStep].link}`);
		}
	}, [payment?.currentStep]);

	return (
		<div className="w-full h-screen xl:max-w-[1200px] mx-auto flex flex-col px-10 md:px-5">
			<PaymentItemsProvider>
				<PayPalScriptProvider
					options={{
						'client-id':
							'AUv8rrc_P-EbP2E0mpb49BV7rFt3Usr-vdUZO8VGOnjRehGHBXkSzchr37SYF2GNdQFYSp72jh5QUhzG',
					}}
				>
					{!orderId ? (
						<div className="flex-shrink-0 pt-10">
							<TopnavCheckout></TopnavCheckout>
							<h1 className="text-center text-2xl">Order id not valid, Please try again!</h1>
						</div>
					) : (
						<>
							<div className="flex-shrink-0 pt-10">
								<TopnavCheckout></TopnavCheckout>
								{step ? (
									checkoutSteps.map((stepItem) => {
										if (stepItem.link === step) {
											return <stepItem.component key={stepItem.id}></stepItem.component>;
										}
									})
								) : (
									<h2>Something went wrong, Please try again!</h2>
								)}
							</div>
							<div className="mt-auto pb-20">
								<MultiStep></MultiStep>
							</div>
						</>
					)}
				</PayPalScriptProvider>
			</PaymentItemsProvider>
		</div>
	);
};

export default CheckoutPage;
