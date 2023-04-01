import * as React from 'react';

interface ICheckoutStepFourProps {}

const CheckoutStepFour: React.FunctionComponent<ICheckoutStepFourProps> = (props) => {
	return (
		<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
			<div className="flex flex-col gap-5 w-full justify-center items-center text-xl">
				<h2>Your order has been checked out successfully</h2>
				<p>Thank you for choosing our services.</p>
				<p>See you soon!</p>
				<p className="flex items-center gap-3 text-primaryff">
					<i className="fa-solid fa-heart"></i>
					<i className="fa-solid fa-heart"></i>
					<i className="fa-solid fa-heart"></i>
				</p>
				<div className="my-5">
					<img srcSet="/images/thanks.jpg 6x" alt="checkout-success" />
				</div>
			</div>
		</div>
	);
};

export default CheckoutStepFour;
