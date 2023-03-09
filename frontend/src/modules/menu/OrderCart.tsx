import useToggleValue from '@hooks/useToggleValue';
import { formatCurrency } from '@utils/formatCurrency';
import * as React from 'react';

interface IOrderCartProps {
	children: React.ReactNode;
	onToggle: () => void;
	totalItem: number;
	totalMoney: number;
}

const OrderCart: React.FunctionComponent<IOrderCartProps> = ({
	children,
	onToggle,
	totalItem = 0,
	totalMoney = 0,
}) => {
	return (
		<>
			<button
				className="fixed right-0 bottom-0 min-w-[200px] bg-slate-200 rounded-lg flex items-center justify-center m-3"
				onClick={onToggle}
			>
				<div className="p-5 flex items-center justify-center gap-3">
					<div className="relative">
						<i className="text-2xl text-gray-500 fas fa-shopping-cart w-10 h-10"></i>
						<span className="absolute -top-2 right-0 bg-red-500 rounded-full text-white text-xs font-bold w-5 h-5 flex items-center justify-center">
							{totalItem}
						</span>
					</div>
					<span>
						<strong>{formatCurrency(totalMoney)}</strong>
					</span>
				</div>
			</button>
			{children}
		</>
	);
};

export default OrderCart;
