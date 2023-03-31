import Button from '@components/button/Button';
import { checkoutSteps } from '@constants/checkoutSteps';
import { usePaymentItems } from '@context/usePaymentItems';
import { usePayment } from '@stores/usePayment';
import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

interface ITopnavProps {
	onBack: () => void;
	onBackToHome: () => void;
	subTitle: string;
	titleMain: string;
	canBack: boolean;
	canBackToHome?: boolean;
}

const Topnav: React.FunctionComponent<ITopnavProps> = ({
	onBack,
	onBackToHome,
	subTitle,
	titleMain,
	canBack = true,
	canBackToHome = true,
}) => {
	return (
		<div className="flex flex-col gap-5">
			<div className="flex items-center justify-between gap-5">
				{canBack ? (
					<Button type="button" onClick={onBack}>
						<div className="flex items-center gap-3">
							<i className="fa fa-angle-left"></i>
							<span>Back</span>
						</div>
					</Button>
				) : (
					<div></div>
				)}
				{canBackToHome ? (
					<Button variant="secondary" type="button" onClick={onBackToHome}>
						<div className="flex items-center gap-3">
							<span>Back to homepage</span>
							<i className="fa fa-refresh"></i>
						</div>
					</Button>
				) : (
					<div></div>
				)}
			</div>
			<div className="flex items-center justify-center gap-3 flex-col">
				<div className="pb-3 px-10 border-b border-b-primaryff">
					<span className="text-2xl font-semibold">{subTitle}</span>
				</div>
				<h2 className="text-3xl font-bold">{titleMain}</h2>
			</div>
		</div>
	);
};

export default Topnav;
