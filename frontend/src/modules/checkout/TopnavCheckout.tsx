import { checkoutSteps } from '@constants/checkoutSteps';
import { usePaymentItems } from '@context/usePaymentItems';
import Topnav from '@modules/common/Topnav';
import { usePayment } from '@stores/usePayment';
import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

interface ITopnavCheckoutProps {}

const TopnavCheckout: React.FunctionComponent<ITopnavCheckoutProps> = () => {
	const {
		paymentItem: { tableName = '' },
	} = usePaymentItems();

	const {
		payment: { currentStep },
		prevStep,
		resetStep,
	} = usePayment();

	const title = React.useMemo(() => {
		return checkoutSteps[currentStep].title;
	}, [currentStep]);

	const navigate = useNavigate();

	const handleBackToPreviousStep = () => {
		if (currentStep === 0) {
			Swal.fire({
				title: 'Are you sure?',
				text: 'Are you definitely want to cancel checkout?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				confirmButtonText: 'Yes',
			}).then((result) => {
				if (result.isConfirmed) {
					resetStep();
					navigate('/');
				}
			});
		} else {
			prevStep();
		}
	};
	const backToHomePage = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Are you definitely want to cancel checkout?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes',
		}).then((result) => {
			if (result.isConfirmed) {
				resetStep();
				navigate('/');
			}
		});
	};
	return (
		<Topnav
			canBack={currentStep !== 0}
			onBack={handleBackToPreviousStep}
			onBackToHome={backToHomePage}
			titleMain={title}
			subTitle={tableName}
		></Topnav>
	);
};

export default TopnavCheckout;
