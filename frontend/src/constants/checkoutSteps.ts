import CheckoutStepFour from '@modules/checkout/CheckoutStepFour';
import CheckoutStepOne from '@modules/checkout/CheckoutStepOne';
import CheckoutStepThree from '@modules/checkout/CheckoutStepThree';
import CheckoutStepTwo from '@modules/checkout/CheckoutStepTwo';

export const checkoutSteps = [
	{
		id: 'step-one',
		title: 'Confirm your order',
		link: 'step-one',
		component: CheckoutStepOne,
	},
	{
		id: 'step-two',
		title: 'Choose your payment method',
		link: 'step-two',
		component: CheckoutStepTwo,
	},
	{
		id: 'step-three',
		title: 'Your receipt',
		link: 'step-three',
		component: CheckoutStepThree,
	},
	{
		id: 'step-four',
		title: 'Order confirmed',
		link: 'step-four',
		component: CheckoutStepFour,
	},
];
