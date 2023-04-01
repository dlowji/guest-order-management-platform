import { usePayment } from '@stores/usePayment';
import * as React from 'react';
import { useNavigate } from 'react-router-dom';

interface ICheckoutStepThreeProps {}

const CheckoutStepThree: React.FunctionComponent<ICheckoutStepThreeProps> = (props) => {
	const navigate = useNavigate();
	const { currentStep, totalSteps, orderId, paymentMethod } = usePayment((state) => state.payment);

	const nextStep = usePayment((state) => state.nextStep);

	React.useEffect(() => {
		if (paymentMethod === 'CASH') {
			nextStep();
			return;
		}
	}, []);

	return (
		<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
			<div className="flex flex-col w-full"></div>
		</div>
	);
};

export default CheckoutStepThree;
