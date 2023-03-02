import { PayPalButtons, usePayPalScriptReducer } from '@paypal/react-paypal-js';
import * as React from 'react';
import { PayPalButtonsComponentOptions } from '@paypal/paypal-js/types';
import DotsWaveLoading from '@components/loading/DotsWaveLoading';

interface IPaypalButtonProps extends PayPalButtonsComponentOptions {
	className?: string;
	currency?: string;
	showSpinner?: boolean;
	amount: string;
}

const PaypalButton: React.FunctionComponent<IPaypalButtonProps> = ({
	currency = 'USD',
	showSpinner = false,
	style,
	amount,
	className,
	createOrder,
	onApprove,
	onError,
}) => {
	const [{ options, isPending }, dispatch] = usePayPalScriptReducer();
	const isLoading = isPending && showSpinner;

	React.useEffect(() => {
		dispatch({
			type: 'resetOptions',
			value: {
				...options,
				currency,
			},
		});
	}, [currency, showSpinner]);
	return (
		<div className="relative">
			{isLoading && (
				<div className="absolute left-0 top-0 z-10 w-full h-full flex items-center justify-center">
					<DotsWaveLoading width={15} height={15}></DotsWaveLoading>
				</div>
			)}
			<PayPalButtons
				style={{
					layout: 'horizontal',
					tagline: false,
				}}
				disabled={isLoading}
				forceReRender={[amount, currency, style]}
				className={`${className} w-full`}
				createOrder={createOrder}
				onApprove={onApprove}
				onError={onError}
			/>
		</div>
	);
};

export default PaypalButton;
